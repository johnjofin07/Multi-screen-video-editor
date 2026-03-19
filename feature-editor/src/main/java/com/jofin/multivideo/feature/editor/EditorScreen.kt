package com.jofin.multivideo.feature.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import com.jofin.multivideo.core.layout.LayoutEngine
import com.jofin.multivideo.core.timeline.TimelineMath
import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.LayoutType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorRoute(
    onBack: () -> Unit,
    onExport: (String) -> Unit,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val project = state.project ?: return
    val selectedClip = project.clips.firstOrNull { it.id == state.previewState.selectedClipId } ?: project.clips.first()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(project.name) },
            navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PreviewCanvas(
                project = project,
                players = viewModel.playerMap(),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = viewModel::onPlayPause) {
                    Text(if (state.previewState.isPlaying) "Pause" else "Play")
                }
                Button(onClick = { viewModel.onSeek((state.previewState.currentPositionMs - 1_000L).coerceAtLeast(0L)) }) {
                    Text("-1s")
                }
                Button(onClick = {
                    val max = TimelineMath.projectDurationMs(project.clips)
                    viewModel.onSeek((state.previewState.currentPositionMs + 1_000L).coerceAtMost(max))
                }) {
                    Text("+1s")
                }
            }
            Slider(
                value = state.previewState.currentPositionMs.toFloat(),
                onValueChange = { viewModel.onSeek(it.toLong()) },
                valueRange = 0f..state.previewState.projectDurationMs.coerceAtLeast(1L).toFloat(),
            )
            Text("${state.previewState.currentPositionMs} / ${state.previewState.projectDurationMs} ms")

            Text("Layout", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LayoutType.entries.forEach { layout ->
                    FilterChip(
                        selected = project.layoutType == layout,
                        onClick = { viewModel.onLayoutSelected(layout) },
                        label = { Text(layout.name.replace('_', ' ')) },
                    )
                }
            }

            Text("Audio Mode", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AudioMode.entries.forEach { mode ->
                    FilterChip(
                        selected = project.audioMode == mode,
                        onClick = { viewModel.onAudioModeSelected(mode) },
                        label = { Text(mode.name.replace('_', ' ')) },
                    )
                }
            }

            Text("Clips", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                project.clips.forEachIndexed { index, clip ->
                    FilterChip(
                        selected = selectedClip.id == clip.id,
                        onClick = { viewModel.onSelectClip(clip.id) },
                        label = { Text("Clip ${index + 1}") },
                    )
                }
            }

            ClipInspector(
                clip = selectedClip,
                isSelectedAudio = project.selectedAudioClipId == selectedClip.id,
                onSelectAudio = { viewModel.onSelectedAudioClip(selectedClip.id) },
                onOffsetAdjust = { viewModel.adjustOffset(selectedClip.id, it) },
                onTrimStart = { viewModel.updateTrimStart(selectedClip.id, it.toLong()) },
                onTrimEnd = { viewModel.updateTrimEnd(selectedClip.id, it.toLong()) },
                onVolume = { viewModel.updateVolume(selectedClip.id, it) },
                onMute = { viewModel.toggleMute(selectedClip.id) },
            )

            Button(onClick = { onExport(project.id) }, modifier = Modifier.fillMaxWidth()) {
                Text("Export")
            }
        }
    }
}

@Composable
private fun PreviewCanvas(
    project: com.jofin.multivideo.domain.model.Project,
    players: Map<String, androidx.media3.exoplayer.ExoPlayer>,
) {
    val slots = LayoutEngine.slotsFor(project.layoutType)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color.Black, RoundedCornerShape(24.dp)),
    ) {
        slots.forEach { slot ->
            val clip = project.clips.firstOrNull { it.slotIndex == slot.slotIndex } ?: return@forEach
            val player = players[clip.id] ?: return@forEach
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        useController = false
                        this.player = player
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(slot.width)
                    .height((280 * slot.height).dp)
                    .offset(x = (320 * slot.x).dp, y = (280 * slot.y).dp)
                    .zIndex(clip.zIndex.toFloat()),
            )
        }
    }
}

@Composable
private fun ClipInspector(
    clip: ClipTrack,
    isSelectedAudio: Boolean,
    onSelectAudio: () -> Unit,
    onOffsetAdjust: (Long) -> Unit,
    onTrimStart: (Float) -> Unit,
    onTrimEnd: (Float) -> Unit,
    onVolume: (Float) -> Unit,
    onMute: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(clip.displayName, style = MaterialTheme.typography.titleMedium)
            Text("Duration: ${clip.durationMs} ms")
            Text("Slot ${clip.slotIndex + 1}")
            TextButton(onClick = onSelectAudio) {
                Text(if (isSelectedAudio) "Active audio source" else "Use as audio source")
            }
            Text("Offset")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                listOf(-1_000L, -500L, -100L, 100L, 500L, 1_000L).forEach { delta ->
                    TextButton(onClick = { onOffsetAdjust(delta) }) {
                        Text(if (delta > 0) "+$delta" else delta.toString())
                    }
                }
            }
            Text("Trim start: ${clip.trimStartMs} ms")
            Slider(
                value = clip.trimStartMs.toFloat(),
                onValueChange = onTrimStart,
                valueRange = 0f..clip.durationMs.toFloat(),
            )
            Text("Trim end: ${clip.trimEndMs} ms")
            Slider(
                value = clip.trimEndMs.toFloat(),
                onValueChange = onTrimEnd,
                valueRange = 0f..clip.durationMs.toFloat(),
            )
            Text("Volume")
            Slider(
                value = clip.volume,
                onValueChange = onVolume,
                valueRange = 0f..1f,
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("Mute")
                Switch(checked = clip.muted, onCheckedChange = { onMute() })
            }
        }
    }
}
