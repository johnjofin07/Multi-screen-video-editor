package com.jofin.multivideo.domain.model

data class Project(
    val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
    val outputAspectRatio: String,
    val exportPreset: ExportPreset,
    val layoutType: LayoutType,
    val audioMode: AudioMode,
    val backgroundColor: Long,
    val clips: List<ClipTrack>,
    val selectedAudioClipId: String? = clips.firstOrNull()?.id,
)

data class ClipTrack(
    val id: String,
    val uri: String,
    val displayName: String,
    val durationMs: Long,
    val width: Int,
    val height: Int,
    val rotationDegrees: Int,
    val trimStartMs: Long = 0L,
    val trimEndMs: Long = 0L,
    val startOffsetMs: Long = 0L,
    val volume: Float = 1f,
    val muted: Boolean = false,
    val slotIndex: Int,
    val zIndex: Int = slotIndex,
    val mimeType: String = "video/*",
    val hasAudio: Boolean = true,
)

enum class LayoutType {
    SIDE_BY_SIDE,
    TOP_BOTTOM,
    PIP_BOTTOM_RIGHT,
    GRID_2X2,
}

data class LayoutSlot(
    val slotIndex: Int,
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val scaleMode: ScaleMode = ScaleMode.CROP,
    val cornerRadiusPx: Float = 0f,
)

enum class ScaleMode {
    FIT,
    CROP,
}

enum class AudioMode {
    FIRST_TRACK_ONLY,
    SELECTED_TRACK_ONLY,
    MIX_ALL,
    MUTE_ALL,
}

data class ExportPreset(
    val label: String,
    val width: Int,
    val height: Int,
    val bitrate: Int,
    val frameRate: Int,
    val audioBitrate: Int,
) {
    companion object {
        val P720 = ExportPreset("720p 30fps", 1280, 720, 5_000_000, 30, 192_000)
        val P1080 = ExportPreset("1080p 30fps", 1920, 1080, 8_000_000, 30, 192_000)
        val defaults = listOf(P720, P1080)
    }
}

data class PreviewState(
    val currentPositionMs: Long = 0L,
    val isPlaying: Boolean = false,
    val projectDurationMs: Long = 0L,
    val activeAudioTrackIndex: Int? = 0,
    val selectedClipId: String? = null,
)

data class VideoMetadata(
    val uri: String,
    val displayName: String,
    val durationMs: Long,
    val width: Int,
    val height: Int,
    val rotationDegrees: Int,
    val mimeType: String,
    val hasAudio: Boolean,
)

data class ProjectSummary(
    val id: String,
    val name: String,
    val updatedAt: Long,
    val layoutType: LayoutType,
    val clipCount: Int,
)

data class ExportStatus(
    val projectId: String,
    val state: ExportState,
    val progress: Int = 0,
    val outputUri: String? = null,
    val message: String? = null,
)

enum class ExportState {
    IDLE,
    RUNNING,
    SUCCESS,
    FAILED,
    CANCELED,
}
