package com.jofin.multivideo.domain.usecase

import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.ExportPreset
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.model.VideoMetadata
import java.util.UUID
import javax.inject.Inject

class CreateProjectFromMediaUseCase @Inject constructor() {
    operator fun invoke(metadata: List<VideoMetadata>, nowMs: Long = System.currentTimeMillis()): Project {
        val clips = metadata.mapIndexed { index, item ->
            ClipTrack(
                id = UUID.randomUUID().toString(),
                uri = item.uri,
                displayName = item.displayName,
                durationMs = item.durationMs,
                width = item.width,
                height = item.height,
                rotationDegrees = item.rotationDegrees,
                slotIndex = index,
                zIndex = index,
                mimeType = item.mimeType,
                hasAudio = item.hasAudio,
            )
        }
        val layoutType = when (clips.size) {
            2 -> LayoutType.SIDE_BY_SIDE
            3, 4 -> LayoutType.GRID_2X2
            else -> LayoutType.SIDE_BY_SIDE
        }
        return Project(
            id = UUID.randomUUID().toString(),
            name = "Project ${metadata.firstOrNull()?.displayName ?: nowMs}",
            createdAt = nowMs,
            updatedAt = nowMs,
            outputAspectRatio = "16:9",
            exportPreset = ExportPreset.P720,
            layoutType = layoutType,
            audioMode = AudioMode.FIRST_TRACK_ONLY,
            backgroundColor = 0xFF000000,
            clips = clips,
            selectedAudioClipId = clips.firstOrNull { it.hasAudio }?.id,
        )
    }
}
