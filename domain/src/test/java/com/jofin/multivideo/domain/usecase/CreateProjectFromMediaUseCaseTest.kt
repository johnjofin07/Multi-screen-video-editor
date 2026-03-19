package com.jofin.multivideo.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.VideoMetadata
import org.junit.Test

class CreateProjectFromMediaUseCaseTest {
    @Test
    fun `two videos default to side by side`() {
        val useCase = CreateProjectFromMediaUseCase()
        val project = useCase(
            listOf(
                VideoMetadata("a", "a.mp4", 1_000, 1920, 1080, 0, "video/mp4", true),
                VideoMetadata("b", "b.mp4", 1_000, 1920, 1080, 0, "video/mp4", true),
            ),
            nowMs = 1L,
        )

        assertThat(project.layoutType).isEqualTo(LayoutType.SIDE_BY_SIDE)
        assertThat(project.clips).hasSize(2)
    }
}
