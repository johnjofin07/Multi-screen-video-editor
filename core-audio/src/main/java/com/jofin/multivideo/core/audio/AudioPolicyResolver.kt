package com.jofin.multivideo.core.audio

import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.Project

data class ResolvedAudioTrack(
    val clipId: String,
    val volume: Float,
)

object AudioPolicyResolver {
    fun resolve(project: Project): List<ResolvedAudioTrack> = when (project.audioMode) {
        AudioMode.FIRST_TRACK_ONLY -> project.clips
            .firstOrNull { it.hasAudio && !it.muted }
            ?.let { listOf(ResolvedAudioTrack(it.id, it.volume.coerceIn(0f, 1f))) }
            .orEmpty()

        AudioMode.SELECTED_TRACK_ONLY -> project.clips
            .firstOrNull { it.id == project.selectedAudioClipId && it.hasAudio && !it.muted }
            ?.let { listOf(ResolvedAudioTrack(it.id, it.volume.coerceIn(0f, 1f))) }
            .orEmpty()

        AudioMode.MIX_ALL -> normalizeMix(
            project.clips.filter { it.hasAudio && !it.muted }.map {
                ResolvedAudioTrack(it.id, it.volume.coerceAtLeast(0f))
            },
        )

        AudioMode.MUTE_ALL -> emptyList()
    }

    private fun normalizeMix(tracks: List<ResolvedAudioTrack>): List<ResolvedAudioTrack> {
        if (tracks.isEmpty()) return tracks
        val sum = tracks.sumOf { it.volume.toDouble() }.toFloat().coerceAtLeast(1f)
        val clamp = if (sum > 1f) 1f / sum else 1f
        return tracks.map { it.copy(volume = (it.volume * clamp).coerceIn(0f, 1f)) }
    }
}
