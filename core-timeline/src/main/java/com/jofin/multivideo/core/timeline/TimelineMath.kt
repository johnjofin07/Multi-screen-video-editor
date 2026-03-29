package com.jofin.multivideo.core.timeline

import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.TrimSegment
import kotlin.math.max

object TimelineMath {
    fun visibleDurationMs(track: ClipTrack): Long =
        (track.durationMs - track.trimStartMs - track.trimEndMs).coerceAtLeast(0L)

    fun sourceVisibleStartMs(track: ClipTrack): Long = track.trimStartMs

    fun sourceVisibleEndMs(track: ClipTrack): Long = (track.durationMs - track.trimEndMs).coerceAtLeast(0L)

    fun timelineStartMs(track: ClipTrack): Long = track.startOffsetMs

    fun timelineEndMs(track: ClipTrack): Long = timelineStartMs(track) + segmentedDurationMs(track)

    /** Total duration accounting for multi-cut segments (gaps between segments are removed). */
    fun segmentedDurationMs(track: ClipTrack): Long = track.segmentedDurationMs()

    fun projectDurationMs(tracks: List<ClipTrack>): Long =
        tracks.fold(0L) { acc, clip -> max(acc, timelineEndMs(clip)) }

    fun isActiveAt(track: ClipTrack, projectTimeMs: Long): Boolean =
        projectTimeMs in timelineStartMs(track) until timelineEndMs(track)

    fun sourcePositionForProjectTime(track: ClipTrack, projectTimeMs: Long): Long? {
        if (!isActiveAt(track, projectTimeMs)) return null
        val relative = projectTimeMs - timelineStartMs(track)
        // Walk through segments to find the right source position
        var accumulated = 0L
        for (seg in track.effectiveSegments()) {
            if (relative < accumulated + seg.durationMs) {
                return seg.startMs + (relative - accumulated)
            }
            accumulated += seg.durationMs
        }
        return null
    }

    fun validateClip(track: ClipTrack): Boolean =
        track.trimStartMs >= 0 &&
            track.trimEndMs >= 0 &&
            segmentedDurationMs(track) > 0

    /**
     * Split a segment at a given source-time position, producing two segments.
     * Returns the updated segment list, or the original if the cut point is invalid.
     */
    fun cutSegmentAt(segments: List<TrimSegment>, segmentId: String, cutPointMs: Long, clipId: String): List<TrimSegment> {
        val idx = segments.indexOfFirst { it.id == segmentId }
        if (idx < 0) return segments
        val seg = segments[idx]
        if (cutPointMs <= seg.startMs || cutPointMs >= seg.endMs) return segments

        val left = seg.copy(
            id = "${clipId}_seg_${System.nanoTime()}_l",
            endMs = cutPointMs,
            selected = false,
        )
        val right = seg.copy(
            id = "${clipId}_seg_${System.nanoTime()}_r",
            startMs = cutPointMs,
            selected = false,
        )
        return segments.toMutableList().apply {
            removeAt(idx)
            add(idx, right)
            add(idx, left)
        }
    }

    /**
     * Merge two adjacent segments into one. Returns updated list or original if not adjacent.
     */
    fun mergeSegments(segments: List<TrimSegment>, segIdA: String, segIdB: String, clipId: String): List<TrimSegment> {
        val idxA = segments.indexOfFirst { it.id == segIdA }
        val idxB = segments.indexOfFirst { it.id == segIdB }
        if (idxA < 0 || idxB < 0) return segments
        val first = minOf(idxA, idxB)
        val second = maxOf(idxA, idxB)
        if (second - first != 1) return segments

        val merged = TrimSegment(
            id = "${clipId}_seg_${System.nanoTime()}_m",
            startMs = segments[first].startMs,
            endMs = segments[second].endMs,
            selected = true,
        )
        return segments.toMutableList().apply {
            removeAt(second)
            removeAt(first)
            add(first, merged)
        }
    }

    /**
     * Delete a segment from the list. Returns updated list.
     * If only one segment remains, it cannot be deleted.
     */
    fun deleteSegment(segments: List<TrimSegment>, segmentId: String): List<TrimSegment> {
        if (segments.size <= 1) return segments
        return segments.filter { it.id != segmentId }
    }

    /** Compute the audio fade multiplier at a given position within the clip's audible timeline. */
    fun fadeMultiplier(track: ClipTrack, positionInClipMs: Long): Float {
        val totalDur = segmentedDurationMs(track)
        if (totalDur <= 0) return 1f
        // Fade in
        if (track.audioFadeInMs > 0 && positionInClipMs < track.audioFadeInMs) {
            return (positionInClipMs.toFloat() / track.audioFadeInMs).coerceIn(0f, 1f)
        }
        // Fade out
        if (track.audioFadeOutMs > 0 && positionInClipMs > totalDur - track.audioFadeOutMs) {
            val remaining = totalDur - positionInClipMs
            return (remaining.toFloat() / track.audioFadeOutMs).coerceIn(0f, 1f)
        }
        return 1f
    }
}
