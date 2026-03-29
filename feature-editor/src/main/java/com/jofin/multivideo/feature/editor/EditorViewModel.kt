package com.jofin.multivideo.feature.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jofin.multivideo.core.layout.LayoutEngine
import com.jofin.multivideo.core.playback.PreviewSessionManager
import com.jofin.multivideo.core.timeline.TimelineMath
import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.PreviewState
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.model.TrimSegment
import com.jofin.multivideo.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class EditorUiState(
    val project: Project? = null,
    val previewState: PreviewState = PreviewState(),
)

@HiltViewModel
class EditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectRepository: ProjectRepository,
    private val previewSessionManager: PreviewSessionManager,
) : ViewModel() {
    private val projectId: String = checkNotNull(savedStateHandle["projectId"])
    private val previewStateFlow = MutableStateFlow(PreviewState())

    val uiState: StateFlow<EditorUiState> =
        combine(
            projectRepository.observeProject(projectId),
            previewStateFlow,
        ) { project, preview ->
            project?.let(previewSessionManager::attachProject)
            EditorUiState(
                project = project,
                previewState = preview.copy(projectDurationMs = project?.let { TimelineMath.projectDurationMs(it.clips) } ?: 0L),
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), EditorUiState())

    fun playerMap() = previewSessionManager.players()

    fun onPlayPause() {
        val current = uiState.value
        val project = current.project ?: return
        val newPlaying = !current.previewState.isPlaying
        if (newPlaying) {
            previewSessionManager.play(project.selectedAudioClipId)
        } else {
            previewSessionManager.pause()
        }
        previewStateFlow.value = current.previewState.copy(isPlaying = newPlaying)
    }

    fun onSeek(projectTimeMs: Long) {
        val project = uiState.value.project ?: return
        previewSessionManager.seekTo(projectTimeMs, project.selectedAudioClipId)
        previewStateFlow.value = previewStateFlow.value.copy(currentPositionMs = projectTimeMs)
    }

    fun onSelectClip(clipId: String) {
        previewStateFlow.value = previewStateFlow.value.copy(selectedClipId = clipId)
    }

    fun onLayoutSelected(layoutType: LayoutType) {
        val project = uiState.value.project ?: return
        if (!LayoutEngine.canPlaceClipCount(layoutType, project.clips.size)) return
        saveProject(project.copy(layoutType = layoutType, updatedAt = System.currentTimeMillis()))
    }

    fun onAudioModeSelected(audioMode: AudioMode) {
        val project = uiState.value.project ?: return
        saveProject(project.copy(audioMode = audioMode, updatedAt = System.currentTimeMillis()))
    }

    fun onSelectedAudioClip(clipId: String) {
        val project = uiState.value.project ?: return
        saveProject(project.copy(selectedAudioClipId = clipId, updatedAt = System.currentTimeMillis()))
    }

    fun adjustOffset(clipId: String, deltaMs: Long) = updateClip(clipId) {
        copy(startOffsetMs = (startOffsetMs + deltaMs).coerceAtLeast(0L))
    }

    fun updateTrimStart(clipId: String, trimStartMs: Long) = updateClip(clipId) {
        copy(trimStartMs = trimStartMs.coerceAtLeast(0L))
    }

    fun updateTrimEnd(clipId: String, trimEndMs: Long) = updateClip(clipId) {
        copy(trimEndMs = trimEndMs.coerceAtLeast(0L))
    }

    fun updateVolume(clipId: String, volume: Float) = updateClip(clipId) {
        copy(volume = volume.coerceIn(0f, 1f))
    }

    fun toggleMute(clipId: String) = updateClip(clipId) {
        copy(muted = !muted)
    }

    fun unmuteClip(clipId: String) = updateClip(clipId) {
        copy(muted = false)
    }

    fun muteClip(clipId: String) = updateClip(clipId) {
        copy(muted = true)
    }

    // ── Multi-cut segment operations ──

    fun cutAtPlayhead(clipId: String) {
        val project = uiState.value.project ?: return
        val clip = project.clips.firstOrNull { it.id == clipId } ?: return
        val playheadMs = previewStateFlow.value.currentPositionMs
        val relativeMs = playheadMs - clip.startOffsetMs
        if (relativeMs <= 0) return

        // Find which segment the playhead falls in
        val segments = clip.effectiveSegments()
        var accumulated = 0L
        for (seg in segments) {
            if (relativeMs < accumulated + seg.durationMs) {
                val cutPointMs = seg.startMs + (relativeMs - accumulated)
                val newSegments = TimelineMath.cutSegmentAt(segments, seg.id, cutPointMs, clipId)
                updateClip(clipId) { copy(segments = newSegments) }
                return
            }
            accumulated += seg.durationMs
        }
    }

    fun selectSegment(clipId: String, segmentId: String) = updateClip(clipId) {
        copy(segments = effectiveSegments().map { it.copy(selected = it.id == segmentId) })
    }

    fun deselectAllSegments(clipId: String) = updateClip(clipId) {
        copy(segments = segments.map { it.copy(selected = false) })
    }

    fun deleteSelectedSegment(clipId: String) = updateClip(clipId) {
        val selected = segments.firstOrNull { it.selected } ?: return@updateClip this
        val newSegments = TimelineMath.deleteSegment(segments, selected.id)
        if (newSegments.size == segments.size) return@updateClip this
        copy(segments = newSegments)
    }

    fun mergeWithNext(clipId: String, segmentId: String) = updateClip(clipId) {
        val idx = segments.indexOfFirst { it.id == segmentId }
        if (idx < 0 || idx >= segments.size - 1) return@updateClip this
        val newSegments = TimelineMath.mergeSegments(segments, segmentId, segments[idx + 1].id, clipId)
        copy(segments = newSegments)
    }

    fun updateAudioFadeIn(clipId: String, fadeInMs: Long) = updateClip(clipId) {
        copy(audioFadeInMs = fadeInMs.coerceAtLeast(0L))
    }

    fun updateAudioFadeOut(clipId: String, fadeOutMs: Long) = updateClip(clipId) {
        copy(audioFadeOutMs = fadeOutMs.coerceAtLeast(0L))
    }

    fun removeClip(clipId: String) {
        val project = uiState.value.project ?: return
        val updatedClips = project.clips
            .filter { it.id != clipId }
            .mapIndexed { index, clip -> clip.copy(slotIndex = index, zIndex = index) }
        if (updatedClips.isEmpty()) return
        val newSelected = if (previewStateFlow.value.selectedClipId == clipId) {
            updatedClips.firstOrNull()?.id
        } else {
            previewStateFlow.value.selectedClipId
        }
        previewStateFlow.value = previewStateFlow.value.copy(selectedClipId = newSelected)
        saveProject(project.copy(
            updatedAt = System.currentTimeMillis(),
            clips = updatedClips,
            selectedAudioClipId = if (project.selectedAudioClipId == clipId) updatedClips.firstOrNull()?.id else project.selectedAudioClipId,
        ))
    }

    override fun onCleared() {
        previewSessionManager.releaseAll()
        super.onCleared()
    }

    private fun updateClip(clipId: String, transform: ClipTrack.() -> ClipTrack) {
        val project = uiState.value.project ?: return
        val updated = project.copy(
            updatedAt = System.currentTimeMillis(),
            clips = project.clips.map { clip -> if (clip.id == clipId) clip.transform() else clip },
        )
        saveProject(updated)
    }

    private fun saveProject(project: Project) {
        viewModelScope.launch {
            projectRepository.saveProject(project)
        }
    }
}
