package com.jofin.multivideo.core.timeline

import com.jofin.multivideo.domain.model.ClipTrack
import kotlin.math.max

object TimelineMath {
    fun visibleDurationMs(track: ClipTrack): Long =
        (track.durationMs - track.trimStartMs - track.trimEndMs).coerceAtLeast(0L)

    fun sourceVisibleStartMs(track: ClipTrack): Long = track.trimStartMs

    fun sourceVisibleEndMs(track: ClipTrack): Long = (track.durationMs - track.trimEndMs).coerceAtLeast(0L)

    fun timelineStartMs(track: ClipTrack): Long = track.startOffsetMs

    fun timelineEndMs(track: ClipTrack): Long = timelineStartMs(track) + visibleDurationMs(track)

    fun projectDurationMs(tracks: List<ClipTrack>): Long =
        tracks.fold(0L) { acc, clip -> max(acc, timelineEndMs(clip)) }

    fun isActiveAt(track: ClipTrack, projectTimeMs: Long): Boolean =
        projectTimeMs in timelineStartMs(track) until timelineEndMs(track)

    fun sourcePositionForProjectTime(track: ClipTrack, projectTimeMs: Long): Long? {
        if (!isActiveAt(track, projectTimeMs)) return null
        val relative = projectTimeMs - timelineStartMs(track)
        return sourceVisibleStartMs(track) + relative
    }

    fun validateClip(track: ClipTrack): Boolean =
        track.trimStartMs >= 0 &&
            track.trimEndMs >= 0 &&
            visibleDurationMs(track) > 0
}
