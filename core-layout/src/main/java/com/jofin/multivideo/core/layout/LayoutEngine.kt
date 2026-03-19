package com.jofin.multivideo.core.layout

import com.jofin.multivideo.domain.model.LayoutSlot
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.ScaleMode

object LayoutEngine {
    fun slotsFor(layoutType: LayoutType): List<LayoutSlot> = when (layoutType) {
        LayoutType.SIDE_BY_SIDE -> listOf(
            LayoutSlot(0, 0f, 0f, 0.5f, 1f, ScaleMode.CROP),
            LayoutSlot(1, 0.5f, 0f, 0.5f, 1f, ScaleMode.CROP),
        )

        LayoutType.TOP_BOTTOM -> listOf(
            LayoutSlot(0, 0f, 0f, 1f, 0.5f, ScaleMode.CROP),
            LayoutSlot(1, 0f, 0.5f, 1f, 0.5f, ScaleMode.CROP),
        )

        LayoutType.PIP_BOTTOM_RIGHT -> listOf(
            LayoutSlot(0, 0f, 0f, 1f, 1f, ScaleMode.CROP),
            LayoutSlot(1, 0.68f, 0.68f, 0.28f, 0.28f, ScaleMode.FIT, cornerRadiusPx = 24f),
        )

        LayoutType.GRID_2X2 -> listOf(
            LayoutSlot(0, 0f, 0f, 0.5f, 0.5f, ScaleMode.CROP),
            LayoutSlot(1, 0.5f, 0f, 0.5f, 0.5f, ScaleMode.CROP),
            LayoutSlot(2, 0f, 0.5f, 0.5f, 0.5f, ScaleMode.CROP),
            LayoutSlot(3, 0.5f, 0.5f, 0.5f, 0.5f, ScaleMode.CROP),
        )
    }

    fun canPlaceClipCount(layoutType: LayoutType, clipCount: Int): Boolean =
        clipCount in 1..slotsFor(layoutType).size
}
