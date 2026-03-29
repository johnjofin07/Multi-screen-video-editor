package com.jofin.multivideo.feature.editor

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMerge
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import com.jofin.multivideo.core.layout.LayoutEngine
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.LayoutType
import com.jofin.multivideo.domain.model.TrimSegment

// ── Color palette ──
private val EditorBackground = Color(0xFF0E0E0E)
private val EditorSurface = Color(0xFF1A1A1A)
private val EditorSurfaceAlt = Color(0xFF242424)
private val EditorBorder = Color(0xFF2A2A2A)
private val EditorText = Color(0xFFF5F5F5)
private val EditorMuted = Color(0xFF8A8A8A)
private val EditorAccent = Color(0xFFFFFFFF)
private val EditorDanger = Color(0xFFE04040)
private val EditorHighlight = Color(0xFF3D8BFF)

private const val ALL_VIDEOS_ID = "__all_videos__"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorRoute(
    onBack: () -> Unit,
    onExport: (String) -> Unit,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val project = state.project ?: return

    val selectedId = state.previewState.selectedClipId ?: project.clips.firstOrNull()?.id
    val isAllSelected = selectedId == ALL_VIDEOS_ID
    val selectedClip = if (isAllSelected) null else project.clips.firstOrNull { it.id == selectedId }
    val selectedClipIndex = if (isAllSelected) -1 else project.clips.indexOfFirst { it.id == selectedId } + 1

    // Track whether "mute others" is active
    val isMuteOthersOn = selectedClip != null && project.clips.any { it.id != selectedClip.id && it.muted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EditorBackground),
    ) {
        EditorHeader(
            projectName = project.name,
            onBack = onBack,
            onExport = { onExport(project.id) },
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            PreviewCanvas(
                project = project,
                players = viewModel.playerMap(),
                selectedClipId = selectedId,
                onSelectClip = { viewModel.onSelectClip(it) },
            )

            PlaybackControls(
                isPlaying = state.previewState.isPlaying,
                currentPositionMs = state.previewState.currentPositionMs,
                durationMs = state.previewState.projectDurationMs,
                onPlayPause = viewModel::onPlayPause,
                onSeek = viewModel::onSeek,
            )

            // Only show layout bar when there are multiple layout choices (<=2 clips)
            val availableLayouts = LayoutType.entries.filter { LayoutEngine.canPlaceClipCount(it, project.clips.size) }
            if (availableLayouts.size > 1) {
                LayoutSelectorBar(
                    currentLayout = project.layoutType,
                    availableLayouts = availableLayouts,
                    onLayoutSelected = viewModel::onLayoutSelected,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ClipSelectorDropdown(
                clips = project.clips,
                selectedId = selectedId ?: project.clips.first().id,
                onSelect = { viewModel.onSelectClip(it) },
            )

            Spacer(modifier = Modifier.height(8.dp))

            AudioControlSection(
                clips = project.clips,
                selectedClip = selectedClip,
                selectedClipIndex = selectedClipIndex,
                isAllSelected = isAllSelected,
                isMuteOthersOn = isMuteOthersOn,
                onMuteOthersToggle = {
                    if (selectedClip != null) {
                        if (isMuteOthersOn) {
                            // Turn off: unmute all
                            project.clips.forEach { c -> viewModel.unmuteClip(c.id) }
                        } else {
                            // Turn on: mute others, unmute selected
                            project.clips.forEach { c ->
                                if (c.id != selectedClip.id) viewModel.muteClip(c.id)
                                else viewModel.unmuteClip(c.id)
                            }
                        }
                    }
                },
                onResetMix = {
                    project.clips.forEach { c ->
                        viewModel.updateVolume(c.id, 1f)
                        viewModel.unmuteClip(c.id)
                    }
                },
                onVolumeChange = { vol ->
                    if (isAllSelected) {
                        project.clips.forEach { c -> viewModel.updateVolume(c.id, vol) }
                    } else if (selectedClip != null) {
                        viewModel.updateVolume(selectedClip.id, vol)
                    }
                },
            )

            if (selectedClip != null) {
                TimingTrimmingSection(
                    clip = selectedClip,
                    clipIndex = selectedClipIndex,
                    canDeleteClip = project.clips.size > 1,
                    currentPositionMs = state.previewState.currentPositionMs,
                    onTrimStartChange = { viewModel.updateTrimStart(selectedClip.id, it) },
                    onTrimEndChange = { viewModel.updateTrimEnd(selectedClip.id, it) },
                    onCutAtPlayhead = { viewModel.cutAtPlayhead(selectedClip.id) },
                    onSelectSegment = { viewModel.selectSegment(selectedClip.id, it) },
                    onDeselectAll = { viewModel.deselectAllSegments(selectedClip.id) },
                    onDeleteSegment = { viewModel.deleteSelectedSegment(selectedClip.id) },
                    onMergeWithNext = { viewModel.mergeWithNext(selectedClip.id, it) },
                    onFadeInChange = { viewModel.updateAudioFadeIn(selectedClip.id, it) },
                    onFadeOutChange = { viewModel.updateAudioFadeOut(selectedClip.id, it) },
                    onDeleteClip = { viewModel.removeClip(selectedClip.id) },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────
// Header
// ─────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditorHeader(projectName: String, onBack: () -> Unit, onExport: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        title = {
            Text(
                "NOIR",
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                color = Color.White,
                letterSpacing = 2.sp,
            )
        },
        actions = {
            TextButton(onClick = onExport) {
                Text(
                    "EXPORT",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    letterSpacing = 1.sp,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = EditorBackground),
    )
}

// ─────────────────────────────────────────────────
// Preview Canvas — with selection highlight & mute badge
// ─────────────────────────────────────────────────

@Composable
private fun PreviewCanvas(
    project: com.jofin.multivideo.domain.model.Project,
    players: Map<String, androidx.media3.exoplayer.ExoPlayer>,
    selectedClipId: String?,
    onSelectClip: (String) -> Unit,
) {
    val slots = LayoutEngine.slotsFor(project.layoutType)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(240.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black),
    ) {
        slots.forEach { slot ->
            val clip = project.clips.firstOrNull { it.slotIndex == slot.slotIndex } ?: return@forEach
            val player = players[clip.id] ?: return@forEach
            val isSelected = clip.id == selectedClipId && selectedClipId != ALL_VIDEOS_ID

            val borderColor by animateColorAsState(
                targetValue = if (isSelected) EditorHighlight else Color.Transparent,
                animationSpec = tween(200),
                label = "previewBorder",
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(slot.width)
                    .height((240 * slot.height).dp)
                    .offset(x = (320 * slot.x).dp, y = (240 * slot.y).dp)
                    .zIndex(clip.zIndex.toFloat())
                    .clip(RoundedCornerShape(6.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(6.dp))
                    .clickable { onSelectClip(clip.id) },
            ) {
                AndroidView(
                    factory = { context ->
                        PlayerView(context).apply {
                            useController = false
                            this.player = player
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                )

                // Timestamp overlay bottom-start
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                ) {
                    Text(
                        text = formatTime(clip.durationMs - clip.trimStartMs - clip.trimEndMs),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                // Mute icon overlay top-end
                if (clip.muted) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.VolumeOff,
                            contentDescription = "Muted",
                            tint = EditorDanger,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────
// Playback Controls
// ─────────────────────────────────────────────────

@Composable
private fun PlaybackControls(
    isPlaying: Boolean,
    currentPositionMs: Long,
    durationMs: Long,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onPlayPause,
            modifier = Modifier
                .size(52.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .background(EditorAccent),
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.Black,
                modifier = Modifier.size(30.dp),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(formatTime(currentPositionMs), color = EditorMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                EditorSlider(
                    value = currentPositionMs.toFloat(),
                    onValueChange = { onSeek(it.toLong()) },
                    valueRange = 0f..durationMs.coerceAtLeast(1L).toFloat(),
                )
            }
            Text(formatTime(durationMs), color = EditorMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

// ─────────────────────────────────────────────────
// Layout Selector — hidden when only 1 option
// ─────────────────────────────────────────────────

@Composable
private fun LayoutSelectorBar(
    currentLayout: LayoutType,
    availableLayouts: List<LayoutType>,
    onLayoutSelected: (LayoutType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(EditorSurface)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        availableLayouts.forEach { layout ->
            val isSelected = currentLayout == layout
            val bgColor by animateColorAsState(
                if (isSelected) EditorAccent else Color.Transparent, tween(250), label = "lBg",
            )
            val iconColor by animateColorAsState(
                if (isSelected) Color.Black else EditorMuted, tween(250), label = "lIc",
            )
            val elev by animateDpAsState(
                if (isSelected) 4.dp else 0.dp, spring(), label = "lEl",
            )

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .shadow(elev, RoundedCornerShape(14.dp))
                    .clip(RoundedCornerShape(14.dp))
                    .background(bgColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onLayoutSelected(layout) },
                contentAlignment = Alignment.Center,
            ) {
                LayoutGlyph(layout, iconColor)
            }
        }
    }
}

@Composable
private fun LayoutGlyph(layout: LayoutType, color: Color) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val s = 2.dp.toPx(); val g = 3.dp.toPx()
        drawRoundRect(color, style = Stroke(s), cornerRadius = CornerRadius(5.dp.toPx()))
        when (layout) {
            LayoutType.SIDE_BY_SIDE -> drawLine(color, Offset(size.width / 2f, g), Offset(size.width / 2f, size.height - g), s, StrokeCap.Round)
            LayoutType.TOP_BOTTOM -> drawLine(color, Offset(g, size.height / 2f), Offset(size.width - g, size.height / 2f), s, StrokeCap.Round)
            LayoutType.PIP_BOTTOM_RIGHT -> drawRoundRect(color, style = Stroke(s), topLeft = Offset(size.width * .56f, size.height * .56f), size = Size(size.width * .28f, size.height * .28f), cornerRadius = CornerRadius(3.dp.toPx()))
            LayoutType.GRID_2X2 -> {
                drawLine(color, Offset(size.width / 2f, g), Offset(size.width / 2f, size.height - g), s, StrokeCap.Round)
                drawLine(color, Offset(g, size.height / 2f), Offset(size.width - g, size.height / 2f), s, StrokeCap.Round)
            }
        }
    }
}

// ─────────────────────────────────────────────────
// Clip Selector Dropdown — no white border issue
// ─────────────────────────────────────────────────

@Composable
private fun ClipSelectorDropdown(
    clips: List<ClipTrack>,
    selectedId: String,
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val label = when (selectedId) {
        ALL_VIDEOS_ID -> "All Videos"
        else -> {
            val idx = clips.indexOfFirst { it.id == selectedId }
            if (idx >= 0) clips[idx].displayName.ifBlank { "Video ${idx + 1}" } else "Select clip"
        }
    }

    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(EditorSurface)
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, color = EditorText, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Expand", tint = EditorMuted, modifier = Modifier.size(22.dp))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(0.dp, 4.dp),
            modifier = Modifier
                .widthIn(min = 200.dp)
                .background(EditorSurface),
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "All Videos",
                        color = if (selectedId == ALL_VIDEOS_ID) EditorHighlight else EditorText,
                        fontWeight = if (selectedId == ALL_VIDEOS_ID) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                onClick = { onSelect(ALL_VIDEOS_ID); expanded = false },
            )
            clips.forEachIndexed { index, clip ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                clip.displayName.ifBlank { "Video ${index + 1}" },
                                color = if (selectedId == clip.id) EditorHighlight else EditorText,
                                fontWeight = if (selectedId == clip.id) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.weight(1f),
                            )
                            if (clip.muted) {
                                Icon(Icons.Filled.VolumeOff, contentDescription = "Muted", tint = EditorDanger, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    onClick = { onSelect(clip.id); expanded = false },
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────
// Audio Control — Mute Others toggles color
// ─────────────────────────────────────────────────

@Composable
private fun AudioControlSection(
    clips: List<ClipTrack>,
    selectedClip: ClipTrack?,
    selectedClipIndex: Int,
    isAllSelected: Boolean,
    isMuteOthersOn: Boolean,
    onMuteOthersToggle: () -> Unit,
    onResetMix: () -> Unit,
    onVolumeChange: (Float) -> Unit,
) {
    val currentVolume = when {
        isAllSelected -> clips.map { it.volume }.average().toFloat()
        selectedClip != null -> selectedClip.volume
        else -> 1f
    }
    val badgeText = if (isAllSelected) "ALL VIDEOS" else "CLIP %02d".format(selectedClipIndex)

    // Animate mute button color
    val muteBg by animateColorAsState(
        targetValue = when {
            !isAllSelected && isMuteOthersOn -> EditorAccent
            !isAllSelected -> EditorSurfaceAlt
            else -> EditorSurfaceAlt.copy(alpha = 0.5f)
        },
        animationSpec = tween(200),
        label = "muteBg",
    )
    val muteText by animateColorAsState(
        targetValue = when {
            !isAllSelected && isMuteOthersOn -> Color.Black
            !isAllSelected -> EditorText
            else -> EditorMuted
        },
        animationSpec = tween(200),
        label = "muteText",
    )

    SectionCard {
        SectionHeader(title = "AUDIO CONTROL", badge = badgeText)
        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Mute Others — toggle button
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(muteBg)
                    .then(if (!isAllSelected && !isMuteOthersOn) Modifier.border(1.dp, EditorBorder, RoundedCornerShape(12.dp)) else Modifier)
                    .then(if (!isAllSelected) Modifier.clickable(onClick = onMuteOthersToggle) else Modifier)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("MUTE OTHERS", color = muteText, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
            }

            // Reset Mix
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EditorSurfaceAlt)
                    .border(1.dp, EditorBorder, RoundedCornerShape(12.dp))
                    .clickable(onClick = onResetMix)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("RESET MIX", color = EditorText, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("CLIP VOLUME", color = EditorMuted, fontSize = 11.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Medium)
            Text("${(currentVolume * 100).toInt()}%", color = EditorText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        EditorSlider(value = currentVolume, onValueChange = onVolumeChange, valueRange = 0f..1f)
    }
}

// ─────────────────────────────────────────────────
// Timing & Trimming — Filmstrip-style redesign
// ─────────────────────────────────────────────────

private val TrimHandle = Color(0xFFD946EF)          // Magenta/purple trim handles
private val TrimHandleLight = Color(0xFFE879F9)
private val SegmentFill = Color(0xFF2A2A2A)          // Segment body fill (dark)
private val SegmentSelectedBorder = Color(0xFFD946EF) // Selected segment border
private val SegmentGap = Color(0xFF111111)            // Gap between segments
private val FadeCurve = Color(0xFF4ECDC4)
private val PlayheadColor = Color.White

@Composable
private fun TimingTrimmingSection(
    clip: ClipTrack,
    clipIndex: Int,
    canDeleteClip: Boolean,
    currentPositionMs: Long,
    onTrimStartChange: (Long) -> Unit,
    onTrimEndChange: (Long) -> Unit,
    onCutAtPlayhead: () -> Unit,
    onSelectSegment: (String) -> Unit,
    onDeselectAll: () -> Unit,
    onDeleteSegment: () -> Unit,
    onMergeWithNext: (String) -> Unit,
    onFadeInChange: (Long) -> Unit,
    onFadeOutChange: (Long) -> Unit,
    onDeleteClip: () -> Unit,
) {
    val segments = clip.effectiveSegments()
    val selectedSeg = segments.firstOrNull { it.selected }
    val selectedIdx = segments.indexOfFirst { it.selected }
    val totalSegDuration = clip.segmentedDurationMs()
    val maxFadeMs = (totalSegDuration / 2).coerceAtLeast(0L)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        // ── Large timestamp display ──
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    formatTimePrecise(currentPositionMs),
                    color = EditorText,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                )
                Text(
                    " / ${formatTimePrecise(totalSegDuration)}",
                    color = EditorMuted,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Filmstrip timeline with trim handles ──
        FilmstripTimeline(
            clip = clip,
            segments = segments,
            currentPositionMs = currentPositionMs,
            onTrimStartChange = onTrimStartChange,
            onTrimEndChange = onTrimEndChange,
            onSelectSegment = onSelectSegment,
            onDeselectAll = onDeselectAll,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Status bar: segment count + trim times ──
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "${segments.size} SEGMENT${if (segments.size != 1) "S" else ""}",
                color = EditorMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
            )
            if (selectedSeg != null) {
                Text(
                    "SEG ${selectedIdx + 1}: ${formatTimePrecise(selectedSeg.startMs)} – ${formatTimePrecise(selectedSeg.endMs)}",
                    color = TrimHandle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )
            } else {
                Text(
                    "TAP SEGMENT TO SELECT",
                    color = EditorMuted.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Contextual action buttons ──
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // CUT — always available, splits at playhead
            ActionChip(
                icon = Icons.Filled.ContentCut,
                label = "CUT",
                onClick = onCutAtPlayhead,
                modifier = Modifier.weight(1f),
            )

            // DELETE segment — needs selection + more than 1 segment
            val canDeleteSeg = selectedSeg != null && segments.size > 1
            ActionChip(
                icon = Icons.Filled.Delete,
                label = "DELETE",
                onClick = { if (canDeleteSeg) onDeleteSegment() },
                enabled = canDeleteSeg,
                danger = true,
                modifier = Modifier.weight(1f),
            )

            // MERGE — needs selected segment with a neighbor after it
            val canMerge = selectedSeg != null && selectedIdx < segments.size - 1
            ActionChip(
                icon = Icons.Filled.CallMerge,
                label = "MERGE",
                onClick = { if (canMerge) onMergeWithNext(selectedSeg!!.id) },
                enabled = canMerge,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Audio Fade Controls ──
        SectionCard {
            Text("AUDIO FADES", color = EditorMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            FadeControl(
                label = "FADE IN",
                valueMs = clip.audioFadeInMs,
                maxMs = maxFadeMs,
                color = FadeCurve,
                onValueChange = onFadeInChange,
            )
            Spacer(modifier = Modifier.height(8.dp))
            FadeControl(
                label = "FADE OUT",
                valueMs = clip.audioFadeOutMs,
                maxMs = maxFadeMs,
                color = FadeCurve,
                onValueChange = onFadeOutChange,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Delete clip ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(if (canDeleteClip) EditorDanger.copy(alpha = 0.1f) else EditorSurfaceAlt)
                .border(1.dp, if (canDeleteClip) EditorDanger.copy(alpha = 0.3f) else EditorBorder, RoundedCornerShape(12.dp))
                .then(if (canDeleteClip) Modifier.clickable(onClick = onDeleteClip) else Modifier)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete clip", tint = if (canDeleteClip) EditorDanger else EditorMuted, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("DELETE CLIP", color = if (canDeleteClip) EditorDanger else EditorMuted, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
        }
    }
}

// ─────────────────────────────────────────────────
// Filmstrip Timeline — segments as filmstrip blocks
// with trim handles, gaps between cuts, playhead
// ─────────────────────────────────────────────────

@Composable
private fun FilmstripTimeline(
    clip: ClipTrack,
    segments: List<TrimSegment>,
    currentPositionMs: Long,
    onTrimStartChange: (Long) -> Unit,
    onTrimEndChange: (Long) -> Unit,
    onSelectSegment: (String) -> Unit,
    onDeselectAll: () -> Unit,
) {
    var widthPx by remember { mutableFloatStateOf(0f) }
    val totalSourceRange = (clip.durationMs - clip.trimStartMs - clip.trimEndMs).coerceAtLeast(1L)
    val sourceStart = clip.trimStartMs
    val trackHeight = 64.dp
    val handleWidthDp = 14.dp

    // Playhead position relative to clip timeline
    val playheadRelative = currentPositionMs - clip.startOffsetMs
    val totalSegDuration = clip.segmentedDurationMs()
    val playheadFrac = if (totalSegDuration > 0) (playheadRelative.toFloat() / totalSegDuration).coerceIn(0f, 1f) else 0f

    // Trim fractions
    val startFrac = if (clip.durationMs > 0) clip.trimStartMs.toFloat() / clip.durationMs else 0f
    val endFrac = if (clip.durationMs > 0) (clip.durationMs - clip.trimEndMs).toFloat() / clip.durationMs else 1f

    // Gap size between segments (in fraction of total source range)
    val gapFracPerSeg = if (segments.size > 1) 0.015f else 0f // 1.5% gap per boundary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(trackHeight + 20.dp) // extra space for playhead triangle above
            .padding(top = 12.dp)         // room for triangle
            .onSizeChanged { widthPx = it.width.toFloat() },
    ) {
        // Main filmstrip canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .clip(RoundedCornerShape(12.dp))
                .pointerInput(segments, clip.trimStartMs, clip.trimEndMs, clip.durationMs) {
                    var dragging: String? = null // "trimStart", "trimEnd", or null
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            if (widthPx <= 0f) return@detectHorizontalDragGestures
                            val handlePx = handleWidthDp.toPx()
                            val leftHandleX = widthPx * startFrac
                            val rightHandleX = widthPx * endFrac
                            dragging = when {
                                kotlin.math.abs(offset.x - leftHandleX) < handlePx * 2.5f -> "trimStart"
                                kotlin.math.abs(offset.x - rightHandleX) < handlePx * 2.5f -> "trimEnd"
                                else -> null
                            }
                        },
                        onDragEnd = { dragging = null },
                        onDragCancel = { dragging = null },
                        onHorizontalDrag = { _, dragAmount ->
                            if (widthPx <= 0f || dragging == null) return@detectHorizontalDragGestures
                            val deltaFrac = dragAmount / widthPx
                            val deltaMs = (deltaFrac * clip.durationMs).toLong()
                            when (dragging) {
                                "trimStart" -> onTrimStartChange(
                                    (clip.trimStartMs + deltaMs).coerceIn(0L, clip.durationMs - clip.trimEndMs - 100L),
                                )
                                "trimEnd" -> onTrimEndChange(
                                    (clip.trimEndMs - deltaMs).coerceIn(0L, clip.durationMs - clip.trimStartMs - 100L),
                                )
                            }
                        },
                    )
                }
                .pointerInput(segments) {
                    detectTapGestures { offset ->
                        if (widthPx <= 0f) return@detectTapGestures
                        // Map tap to source timeline position
                        val tapFrac = offset.x / widthPx
                        val tapSourceMs = sourceStart + (tapFrac * totalSourceRange).toLong()
                        val tapped = segments.firstOrNull { tapSourceMs in it.startMs..it.endMs }
                        if (tapped != null) onSelectSegment(tapped.id) else onDeselectAll()
                    }
                },
        ) {
            val w = size.width
            val h = size.height
            val r = 12.dp.toPx()
            val handleW = handleWidthDp.toPx()
            val segGapPx = 6.dp.toPx() // visible gap between segments

            // Full background (dimmed/trimmed regions)
            drawRoundRect(
                color = Color(0xFF0A0A0A),
                cornerRadius = CornerRadius(r, r),
            )

            // Draw each segment as a separate filmstrip block with gaps
            val totalGaps = (segments.size - 1).coerceAtLeast(0)
            val totalGapPx = totalGaps * segGapPx
            val availableW = w - totalGapPx

            var xOffset = 0f
            segments.forEachIndexed { index, seg ->
                val segFrac = if (totalSourceRange > 0) seg.durationMs.toFloat() / totalSourceRange else 1f / segments.size
                val segW = (availableW * segFrac).coerceAtLeast(8.dp.toPx())
                val isSelected = seg.selected
                val segCorner = 8.dp.toPx()

                // Segment body — darker fill with subtle gradient feel
                val segColor = if (isSelected) Color(0xFF1E1E2E) else SegmentFill
                drawRoundRect(
                    color = segColor,
                    topLeft = Offset(xOffset, 0f),
                    size = Size(segW, h),
                    cornerRadius = CornerRadius(segCorner, segCorner),
                )

                // Inner filmstrip grain lines (subtle vertical ticks to simulate frames)
                val tickSpacing = 20.dp.toPx()
                var tickX = xOffset + tickSpacing
                while (tickX < xOffset + segW - 2.dp.toPx()) {
                    drawLine(
                        color = Color.White.copy(alpha = 0.04f),
                        start = Offset(tickX, 2.dp.toPx()),
                        end = Offset(tickX, h - 2.dp.toPx()),
                        strokeWidth = 1.dp.toPx(),
                    )
                    tickX += tickSpacing
                }

                // Selection highlight — magenta border
                if (isSelected) {
                    drawRoundRect(
                        color = SegmentSelectedBorder,
                        topLeft = Offset(xOffset, 0f),
                        size = Size(segW, h),
                        cornerRadius = CornerRadius(segCorner, segCorner),
                        style = Stroke(2.dp.toPx()),
                    )
                    // Top/bottom accent bars
                    val barH = 3.dp.toPx()
                    drawRoundRect(
                        color = TrimHandle,
                        topLeft = Offset(xOffset, 0f),
                        size = Size(segW, barH),
                        cornerRadius = CornerRadius(segCorner, segCorner),
                    )
                    drawRoundRect(
                        color = TrimHandle,
                        topLeft = Offset(xOffset, h - barH),
                        size = Size(segW, barH),
                        cornerRadius = CornerRadius(segCorner, segCorner),
                    )
                }

                // Segment label inside
                // (We'll draw time markers below the canvas instead for clarity)

                xOffset += segW + segGapPx
            }

            // ── Trim handles (left and right) ──
            // Left handle
            val leftHandleX = 0f
            val handleCorner = CornerRadius(r, r)
            drawRoundRect(
                color = TrimHandle,
                topLeft = Offset(leftHandleX, 0f),
                size = Size(handleW, h),
                cornerRadius = handleCorner,
            )
            // Grip lines on left handle
            val gripW = 2.5.dp.toPx()
            val gripH = h * 0.35f
            val gripR = CornerRadius(gripW / 2, gripW / 2)
            drawRoundRect(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = Offset(leftHandleX + (handleW - gripW) / 2, (h - gripH) / 2),
                size = Size(gripW, gripH),
                cornerRadius = gripR,
            )

            // Right handle
            val rightHandleX = w - handleW
            drawRoundRect(
                color = TrimHandle,
                topLeft = Offset(rightHandleX, 0f),
                size = Size(handleW, h),
                cornerRadius = handleCorner,
            )
            drawRoundRect(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = Offset(rightHandleX + (handleW - gripW) / 2, (h - gripH) / 2),
                size = Size(gripW, gripH),
                cornerRadius = gripR,
            )

            // Top and bottom border lines between handles
            val borderStroke = 2.5.dp.toPx()
            drawRect(
                color = TrimHandle,
                topLeft = Offset(handleW, 0f),
                size = Size(w - handleW * 2, borderStroke),
            )
            drawRect(
                color = TrimHandle,
                topLeft = Offset(handleW, h - borderStroke),
                size = Size(w - handleW * 2, borderStroke),
            )

            // ── Playhead ──
            val phX = (handleW + (w - handleW * 2) * playheadFrac).coerceIn(handleW, w - handleW)
            drawLine(
                color = PlayheadColor,
                start = Offset(phX, 0f),
                end = Offset(phX, h),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
            )
            // Playhead triangle above
            val triSize = 6.dp.toPx()
            val triPath = Path().apply {
                moveTo(phX - triSize, -triSize * 0.3f)
                lineTo(phX + triSize, -triSize * 0.3f)
                lineTo(phX, triSize * 0.8f)
                close()
            }
            drawPath(triPath, PlayheadColor)

            // Audio fade overlays
            val contentW = w - handleW * 2
            val contentStart = handleW
            if (clip.audioFadeInMs > 0 && totalSegDuration > 0) {
                val fadeInFrac = (clip.audioFadeInMs.toFloat() / totalSourceRange).coerceIn(0f, 0.5f)
                val fadePath = Path().apply {
                    moveTo(contentStart, h)
                    lineTo(contentStart + contentW * fadeInFrac, 0f)
                    lineTo(contentStart, 0f)
                    close()
                }
                drawPath(fadePath, FadeCurve.copy(alpha = 0.12f), style = Fill)
                drawLine(FadeCurve.copy(alpha = 0.6f), Offset(contentStart, h), Offset(contentStart + contentW * fadeInFrac, 0f), 1.5.dp.toPx(), StrokeCap.Round)
            }
            if (clip.audioFadeOutMs > 0 && totalSegDuration > 0) {
                val fadeOutFrac = (clip.audioFadeOutMs.toFloat() / totalSourceRange).coerceIn(0f, 0.5f)
                val contentEnd = contentStart + contentW
                val fadePath = Path().apply {
                    moveTo(contentEnd, h)
                    lineTo(contentEnd - contentW * fadeOutFrac, 0f)
                    lineTo(contentEnd, 0f)
                    close()
                }
                drawPath(fadePath, FadeCurve.copy(alpha = 0.12f), style = Fill)
                drawLine(FadeCurve.copy(alpha = 0.6f), Offset(contentEnd, h), Offset(contentEnd - contentW * fadeOutFrac, 0f), 1.5.dp.toPx(), StrokeCap.Round)
            }
        }

        // ── Time markers below timeline ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                formatTimePrecise(clip.trimStartMs),
                color = EditorMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
            )
            if (segments.size > 1) {
                // Show playhead time in the middle
                Text(
                    formatTimePrecise(currentPositionMs),
                    color = TrimHandle,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                formatTimePrecise(clip.durationMs - clip.trimEndMs),
                color = EditorMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

// ─────────────────────────────────────────────────
// Action Chip — small icon+label button
// ─────────────────────────────────────────────────

@Composable
private fun ActionChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    danger: Boolean = false,
) {
    val bgColor = when {
        danger && enabled -> EditorDanger.copy(alpha = 0.12f)
        else -> EditorSurfaceAlt
    }
    val borderColor = when {
        danger && enabled -> EditorDanger.copy(alpha = 0.35f)
        else -> EditorBorder
    }
    val contentColor = when {
        !enabled -> EditorMuted.copy(alpha = 0.5f)
        danger -> EditorDanger
        else -> EditorText
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = label, tint = contentColor, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, color = contentColor, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 1.sp)
    }
}

// ─────────────────────────────────────────────────
// Fade Control — labeled slider with curve preview
// ─────────────────────────────────────────────────

@Composable
private fun FadeControl(
    label: String,
    valueMs: Long,
    maxMs: Long,
    color: Color,
    onValueChange: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(EditorBackground)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Mini curve preview
        Canvas(modifier = Modifier.size(28.dp)) {
            val w = size.width
            val h = size.height
            val isIn = label.contains("IN")
            val curvePath = Path().apply {
                if (isIn) {
                    moveTo(0f, h)
                    cubicTo(w * 0.3f, h, w * 0.7f, 0f, w, 0f)
                } else {
                    moveTo(0f, 0f)
                    cubicTo(w * 0.3f, 0f, w * 0.7f, h, w, h)
                }
            }
            drawPath(curvePath, color.copy(alpha = 0.8f), style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(label, color = EditorMuted, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
                Text(
                    if (valueMs > 0) "${valueMs / 100 * 100}ms" else "OFF",
                    color = if (valueMs > 0) color else EditorMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            EditorSlider(
                value = valueMs.toFloat(),
                onValueChange = { onValueChange(it.toLong()) },
                valueRange = 0f..maxMs.coerceAtLeast(1L).toFloat(),
            )
        }
    }
}


// ─────────────────────────────────────────────────
// Shared: Section Card & Header
// ─────────────────────────────────────────────────

@Composable
private fun SectionCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, EditorBorder, RoundedCornerShape(20.dp))
            .background(EditorSurface)
            .padding(16.dp),
    ) { content() }
}

@Composable
private fun SectionHeader(title: String, badge: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, color = EditorText, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 2.sp)
        Text(
            badge, color = EditorMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp,
            modifier = Modifier.border(1.dp, EditorBorder, RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

// ─────────────────────────────────────────────────
// Shared: Slider — clean round knob
// ─────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditorSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        modifier = modifier.fillMaxWidth().height(28.dp),
        colors = SliderDefaults.colors(
            thumbColor = EditorAccent,
            activeTrackColor = EditorAccent,
            inactiveTrackColor = Color(0xFF2E2E2E),
            activeTickColor = Color.Transparent,
            inactiveTickColor = Color.Transparent,
        ),
        thumb = {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(EditorAccent, CircleShape),
            )
        },
    )
}

// ─────────────────────────────────────────────────
// Utility
// ─────────────────────────────────────────────────

private fun formatTime(ms: Long): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return "%02d:%02d:%02d".format(h, m, s)
}

private fun formatTimePrecise(ms: Long): String {
    val totalMs = ms.coerceAtLeast(0)
    val h = totalMs / 3_600_000
    val m = (totalMs % 3_600_000) / 60_000
    val s = (totalMs % 60_000) / 1_000
    val cs = (totalMs % 1_000) / 10 // centiseconds
    return "%02d:%02d:%02d".format(h, m, s)
}
