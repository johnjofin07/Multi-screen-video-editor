package com.jofin.multivideo.core.audio

import com.google.common.truth.Truth.assertThat
import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.ExportPreset
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.Project
import org.junit.Test

class AudioPolicyResolverTest {
    private fun project(mode: AudioMode) = Project(
        id = "p1",
        name = "Test",
        createdAt = 0L,
        updatedAt = 0L,
        outputAspectRatio = "16:9",
        exportPreset = ExportPreset.P720,
        layoutType = LayoutType.SIDE_BY_SIDE,
        audioMode = mode,
        backgroundColor = 0L,
        selectedAudioClipId = "b",
        clips = listOf(
            ClipTrack("a", "uri://a", "A", 1_000, 1, 1, 0, slotIndex = 0, volume = 1f, hasAudio = true),
            ClipTrack("b", "uri://b", "B", 1_000, 1, 1, 0, slotIndex = 1, volume = 0.5f, hasAudio = true),
        ),
    )

    @Test
    fun `selected track mode returns one chosen track`() {
        val resolved = AudioPolicyResolver.resolve(project(AudioMode.SELECTED_TRACK_ONLY))
        assertThat(resolved).hasSize(1)
        assertThat(resolved.first().clipId).isEqualTo("b")
    }

    @Test
    fun `mute all returns empty list`() {
        assertThat(AudioPolicyResolver.resolve(project(AudioMode.MUTE_ALL))).isEmpty()
    }
}
