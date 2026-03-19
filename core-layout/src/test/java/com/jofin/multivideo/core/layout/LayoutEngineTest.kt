package com.jofin.multivideo.core.layout

import com.google.common.truth.Truth.assertThat
import com.jofin.multivideo.domain.model.LayoutType
import org.junit.Test

class LayoutEngineTest {
    @Test
    fun `pip layout exposes two slots`() {
        assertThat(LayoutEngine.slotsFor(LayoutType.PIP_BOTTOM_RIGHT)).hasSize(2)
    }

    @Test
    fun `grid layout accepts four clips`() {
        assertThat(LayoutEngine.canPlaceClipCount(LayoutType.GRID_2X2, 4)).isTrue()
    }

    @Test
    fun `side by side rejects three clips`() {
        assertThat(LayoutEngine.canPlaceClipCount(LayoutType.SIDE_BY_SIDE, 3)).isFalse()
    }
}
