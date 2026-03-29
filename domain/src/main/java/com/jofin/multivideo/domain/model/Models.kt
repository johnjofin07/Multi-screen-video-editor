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

/**
 * A segment represents a kept (non-trimmed) portion of a clip's source timeline.
 * Multiple segments enable multi-cut editing: the user can slice a clip into
 * several pieces, delete unwanted middle sections, and merge adjacent segments.
 */
data class TrimSegment(
    val id: String,
    val startMs: Long,
    val endMs: Long,
    val selected: Boolean = false,
) {
    val durationMs: Long get() = (endMs - startMs).coerceAtLeast(0L)
}

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
    /** Multi-cut segments within the visible (trimmed) range. Empty list = single full segment. */
    val segments: List<TrimSegment> = emptyList(),
    /** Audio fade-in duration in ms applied at the start of the clip's audible range. */
    val audioFadeInMs: Long = 0L,
    /** Audio fade-out duration in ms applied at the end of the clip's audible range. */
    val audioFadeOutMs: Long = 0L,
) {
    /** Effective segments: if the list is empty, treat the entire visible range as one segment. */
    fun effectiveSegments(): List<TrimSegment> =
        segments.ifEmpty {
            listOf(
                TrimSegment(
                    id = "${id}_seg_0",
                    startMs = trimStartMs,
                    endMs = durationMs - trimEndMs,
                ),
            )
        }

    /** Total visible duration across all segments. */
    fun segmentedDurationMs(): Long = effectiveSegments().sumOf { it.durationMs }

    /** Index of the currently selected segment, or -1 if none. */
    fun selectedSegmentIndex(): Int = segments.indexOfFirst { it.selected }
}

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

enum class ExportMode {
    VIDEO,
    AUDIO_ONLY,
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
        val P480 = ExportPreset("480p 30fps", 854, 480, 2_500_000, 30, 128_000)
        val P720 = ExportPreset("720p 30fps", 1280, 720, 5_000_000, 30, 192_000)
        val P1080 = ExportPreset("1080p 30fps", 1920, 1080, 8_000_000, 30, 192_000)
        val P1080_60 = ExportPreset("1080p 60fps", 1920, 1080, 12_000_000, 60, 192_000)
        val P4K = ExportPreset("4K 30fps", 3840, 2160, 35_000_000, 30, 320_000)
        val defaults = listOf(P480, P720, P1080, P1080_60, P4K)

        val AUDIO_128 = ExportPreset("MP3 128kbps", 0, 0, 0, 0, 128_000)
        val AUDIO_192 = ExportPreset("MP3 192kbps", 0, 0, 0, 0, 192_000)
        val AUDIO_320 = ExportPreset("MP3 320kbps", 0, 0, 0, 0, 320_000)
        val audioDefaults = listOf(AUDIO_128, AUDIO_192, AUDIO_320)
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
