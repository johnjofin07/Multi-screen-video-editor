package com.jofin.multivideo.core.timeline

import com.google.common.truth.Truth.assertThat
import com.jofin.multivideo.domain.model.ClipTrack
import org.junit.Test

class TimelineMathTest {
    private val clip = ClipTrack(
        id = "1",
        uri = "content://video/1",
        displayName = "clip.mp4",
        durationMs = 10_000L,
        width = 1920,
        height = 1080,
        rotationDegrees = 0,
        trimStartMs = 1_000L,
        trimEndMs = 2_000L,
        startOffsetMs = 500L,
        slotIndex = 0,
    )

    @Test
    fun `visible duration subtracts trims`() {
        assertThat(TimelineMath.visibleDurationMs(clip)).isEqualTo(7_000L)
    }

    @Test
    fun `project time maps into source position`() {
        assertThat(TimelineMath.sourcePositionForProjectTime(clip, 1_500L)).isEqualTo(2_000L)
    }

    @Test
    fun `inactive time returns null position`() {
        assertThat(TimelineMath.sourcePositionForProjectTime(clip, 9_000L)).isNull()
    }
}
