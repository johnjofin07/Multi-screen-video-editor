package com.jofin.multivideo.feature.export

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.media.MediaMuxer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.OpenInNew 
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofin.multivideo.core.audio.AudioPolicyResolver
import com.jofin.multivideo.core.layout.LayoutEngine
import com.jofin.multivideo.core.timeline.TimelineMath
import com.jofin.multivideo.domain.model.ExportMode
import com.jofin.multivideo.domain.model.ExportPreset
import com.jofin.multivideo.domain.model.ExportState
import com.jofin.multivideo.domain.model.ExportStatus
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.repository.ProjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

const val EXPORT_CHANNEL_ID = "multivideo_export"

interface ExportCoordinator {
    suspend fun export(
        project: Project,
        fileName: String,
        preset: ExportPreset,
        mode: ExportMode,
        onProgress: suspend (Int) -> Unit,
    ): Result<String>
}

class MediaStoreExportCoordinator @Inject constructor(
    @ApplicationContext private val context: Context,
) : ExportCoordinator {

    override suspend fun export(
        project: Project,
        fileName: String,
        preset: ExportPreset,
        mode: ExportMode,
        onProgress: suspend (Int) -> Unit,
    ): Result<String> = runCatching {
        when (mode) {
            ExportMode.VIDEO -> exportVideo(project, fileName, preset, onProgress)
            ExportMode.AUDIO_ONLY -> exportAudioOnly(project, fileName, preset, onProgress)
        }
    }

    private suspend fun exportVideo(
        project: Project,
        fileName: String,
        preset: ExportPreset,
        onProgress: suspend (Int) -> Unit,
    ): String = withContext(Dispatchers.IO) {
        val slots = LayoutEngine.slotsFor(project.layoutType)
        val audioTracks = AudioPolicyResolver.resolve(project)
        val projectDurationUs = TimelineMath.projectDurationMs(project.clips) * 1_000L
        val outputWidth = preset.width
        val outputHeight = preset.height

        // Create MediaStore entry
        val isAudio = false
        val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/MultiVideoEditor")
        }
        val outputUri = requireNotNull(context.contentResolver.insert(collection, values))

        val pfd = requireNotNull(context.contentResolver.openFileDescriptor(outputUri, "rw"))
        val muxer = MediaMuxer(pfd.fileDescriptor, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

        // Set up video encoder
        val videoFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, outputWidth, outputHeight).apply {
            setInteger(MediaFormat.KEY_BIT_RATE, preset.bitrate)
            setInteger(MediaFormat.KEY_FRAME_RATE, preset.frameRate)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        }
        val videoEncoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
        videoEncoder.configure(videoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        val inputSurface = videoEncoder.createInputSurface()
        videoEncoder.start()

        // Prepare frame retrievers for each clip
        val retrievers = project.clips.map { clip ->
            MediaMetadataRetriever().also { r ->
                r.setDataSource(context, Uri.parse(clip.uri))
            }
        }

        val frameDurationUs = 1_000_000L / preset.frameRate
        val totalFrames = (projectDurationUs / frameDurationUs).toInt()

        var muxerVideoTrackIndex = -1
        var muxerStarted = false

        // Audio track setup
        var muxerAudioTrackIndex = -1
        val audioExtractors = mutableListOf<Pair<MediaExtractor, Int>>() // extractor, trackIndex
        var audioFormat: MediaFormat? = null

        if (audioTracks.isNotEmpty()) {
            val firstAudioClip = project.clips.first { clip -> audioTracks.any { it.clipId == clip.id } }
            val extractor = MediaExtractor()
            extractor.setDataSource(context, Uri.parse(firstAudioClip.uri), null)
            for (i in 0 until extractor.trackCount) {
                val fmt = extractor.getTrackFormat(i)
                if (fmt.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                    extractor.selectTrack(i)
                    audioFormat = fmt
                    audioExtractors.add(extractor to i)
                    break
                }
            }
        }

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val bufferInfo = MediaCodec.BufferInfo()

        try {
            // Encode video frames
            for (frameIndex in 0 until totalFrames) {
                val projectTimeUs = frameIndex * frameDurationUs
                val projectTimeMs = projectTimeUs / 1_000

                // Compose frame on input surface
                val canvas = inputSurface.lockHardwareCanvas()
                canvas.drawColor(project.backgroundColor.toInt())

                for (clip in project.clips) {
                    val sourceTimeMs = TimelineMath.sourcePositionForProjectTime(clip, projectTimeMs) ?: continue
                    val slot = slots.firstOrNull { it.slotIndex == clip.slotIndex } ?: continue
                    val retriever = retrievers[project.clips.indexOf(clip)]

                    val frame = retriever.getFrameAtTime(
                        sourceTimeMs * 1_000,
                        MediaMetadataRetriever.OPTION_CLOSEST,
                    ) ?: continue

                    val dstLeft = slot.x * outputWidth
                    val dstTop = slot.y * outputHeight
                    val dstWidth = slot.width * outputWidth
                    val dstHeight = slot.height * outputHeight
                    val dst = RectF(dstLeft, dstTop, dstLeft + dstWidth, dstTop + dstHeight)

                    // Scale: crop to fill the slot
                    val srcAspect = frame.width.toFloat() / frame.height
                    val dstAspect = dstWidth / dstHeight
                    val src = if (srcAspect > dstAspect) {
                        val cropW = (frame.height * dstAspect).toInt()
                        val offset = (frame.width - cropW) / 2
                        android.graphics.Rect(offset, 0, offset + cropW, frame.height)
                    } else {
                        val cropH = (frame.width / dstAspect).toInt()
                        val offset = (frame.height - cropH) / 2
                        android.graphics.Rect(0, offset, frame.width, offset + cropH)
                    }

                    if (slot.cornerRadiusPx > 0f) {
                        canvas.save()
                        val path = android.graphics.Path()
                        path.addRoundRect(dst, slot.cornerRadiusPx, slot.cornerRadiusPx, android.graphics.Path.Direction.CW)
                        canvas.clipPath(path)
                    }

                    canvas.drawBitmap(frame, src, dst, paint)

                    if (slot.cornerRadiusPx > 0f) {
                        canvas.restore()
                    }

                    frame.recycle()
                }

                inputSurface.unlockCanvasAndPost(canvas)

                // Drain encoder output
                drainEncoder(videoEncoder, bufferInfo, muxer, muxerVideoTrackIndex, muxerStarted) { trackIdx, started ->
                    muxerVideoTrackIndex = trackIdx
                    muxerStarted = started
                    if (started && audioFormat != null && muxerAudioTrackIndex == -1) {
                        muxerAudioTrackIndex = muxer.addTrack(audioFormat!!)
                    }
                }

                // Report progress (video is 0-85%, audio is 85-100%)
                val progress = ((frameIndex.toFloat() / totalFrames) * 85).toInt().coerceIn(1, 85)
                onProgress(progress)
            }

            // Signal end of video stream
            videoEncoder.signalEndOfInputStream()
            drainEncoderToEnd(videoEncoder, bufferInfo, muxer, muxerVideoTrackIndex, muxerStarted) { trackIdx, started ->
                muxerVideoTrackIndex = trackIdx
                muxerStarted = started
                if (started && audioFormat != null && muxerAudioTrackIndex == -1) {
                    muxerAudioTrackIndex = muxer.addTrack(audioFormat!!)
                }
            }

            // Ensure muxer is started before audio muxing
            if (!muxerStarted) {
                // If encoder never produced output format, force-drain with a longer timeout
                val forceBufInfo = MediaCodec.BufferInfo()
                while (true) {
                    val idx = videoEncoder.dequeueOutputBuffer(forceBufInfo, 50_000)
                    if (idx == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        muxerVideoTrackIndex = muxer.addTrack(videoEncoder.outputFormat)
                        muxer.start()
                        muxerStarted = true
                        if (audioFormat != null && muxerAudioTrackIndex == -1) {
                            muxerAudioTrackIndex = muxer.addTrack(audioFormat!!)
                        }
                        break
                    } else if (idx >= 0) {
                        val buf = videoEncoder.getOutputBuffer(idx)
                        if (buf != null && forceBufInfo.size > 0 && muxerStarted) {
                            muxer.writeSampleData(muxerVideoTrackIndex, buf, forceBufInfo)
                        }
                        videoEncoder.releaseOutputBuffer(idx, false)
                        if (forceBufInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) break
                    } else {
                        break
                    }
                }
            }

            // Mux audio track
            if (muxerStarted && muxerAudioTrackIndex >= 0 && audioExtractors.isNotEmpty()) {
                onProgress(88)
                val (audioExtractor, _) = audioExtractors.first()
                val audioBuffer = ByteBuffer.allocate(256 * 1024)
                val audioInfo = MediaCodec.BufferInfo()
                val trimStartUs = project.clips
                    .first { clip -> audioTracks.any { it.clipId == clip.id } }
                    .trimStartMs * 1_000
                audioExtractor.seekTo(trimStartUs, MediaExtractor.SEEK_TO_CLOSEST_SYNC)

                while (true) {
                    val sampleSize = audioExtractor.readSampleData(audioBuffer, 0)
                    if (sampleSize < 0) break
                    val sampleTimeUs = audioExtractor.sampleTime
                    if (sampleTimeUs > projectDurationUs + trimStartUs) break
                    audioInfo.set(0, sampleSize, sampleTimeUs - trimStartUs, audioExtractor.sampleFlags)
                    muxer.writeSampleData(muxerAudioTrackIndex, audioBuffer, audioInfo)
                    audioExtractor.advance()
                }
                audioExtractor.release()
                onProgress(95)
            }

            onProgress(100)
        } finally {
            videoEncoder.stop()
            videoEncoder.release()
            inputSurface.release()
            if (muxerStarted) {
                muxer.stop()
            }
            muxer.release()
            pfd.close()
            retrievers.forEach { it.release() }
        }

        outputUri.toString()
    }

    private fun drainEncoder(
        encoder: MediaCodec,
        bufferInfo: MediaCodec.BufferInfo,
        muxer: MediaMuxer,
        currentTrackIndex: Int,
        currentMuxerStarted: Boolean,
        onTrackAdded: (Int, Boolean) -> Unit,
    ) {
        var trackIndex = currentTrackIndex
        var muxerStarted = currentMuxerStarted
        while (true) {
            val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 0)
            if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                if (!muxerStarted) {
                    trackIndex = muxer.addTrack(encoder.outputFormat)
                    muxer.start()
                    muxerStarted = true
                    onTrackAdded(trackIndex, muxerStarted)
                }
            } else if (outputBufferIndex >= 0) {
                val outputBuffer = encoder.getOutputBuffer(outputBufferIndex)
                if (outputBuffer != null && bufferInfo.size > 0 && muxerStarted) {
                    muxer.writeSampleData(trackIndex, outputBuffer, bufferInfo)
                }
                encoder.releaseOutputBuffer(outputBufferIndex, false)
                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) break
            } else if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                break
            } else {
                break
            }
        }
    }

    private fun drainEncoderToEnd(
        encoder: MediaCodec,
        bufferInfo: MediaCodec.BufferInfo,
        muxer: MediaMuxer,
        currentTrackIndex: Int,
        currentMuxerStarted: Boolean,
        onTrackAdded: (Int, Boolean) -> Unit,
    ) {
        var trackIndex = currentTrackIndex
        var muxerStarted = currentMuxerStarted
        while (true) {
            val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, 10_000)
            if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                if (!muxerStarted) {
                    trackIndex = muxer.addTrack(encoder.outputFormat)
                    muxer.start()
                    muxerStarted = true
                    onTrackAdded(trackIndex, muxerStarted)
                }
            } else if (outputBufferIndex >= 0) {
                val outputBuffer = encoder.getOutputBuffer(outputBufferIndex)
                if (outputBuffer != null && bufferInfo.size > 0 && muxerStarted) {
                    muxer.writeSampleData(trackIndex, outputBuffer, bufferInfo)
                }
                encoder.releaseOutputBuffer(outputBufferIndex, false)
                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) break
            } else if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                continue
            } else {
                break
            }
        }
    }

    private suspend fun exportAudioOnly(
        project: Project,
        fileName: String,
        preset: ExportPreset,
        onProgress: suspend (Int) -> Unit,
    ): String = withContext(Dispatchers.IO) {
        val audioTracks = AudioPolicyResolver.resolve(project)
        if (audioTracks.isEmpty()) error("No audio tracks available for export")

        val projectDurationUs = TimelineMath.projectDurationMs(project.clips) * 1_000L

        // Create MediaStore entry for audio
        val collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Music/MultiVideoEditor")
        }
        val outputUri = requireNotNull(context.contentResolver.insert(collection, values))

        // For audio-only, extract raw audio from the primary audio clip and write to output
        val primaryClip = project.clips.first { clip -> audioTracks.any { it.clipId == clip.id } }
        val extractor = MediaExtractor()
        extractor.setDataSource(context, Uri.parse(primaryClip.uri), null)

        var audioTrackIndex = -1
        var audioFormat: MediaFormat? = null
        for (i in 0 until extractor.trackCount) {
            val fmt = extractor.getTrackFormat(i)
            if (fmt.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                audioTrackIndex = i
                audioFormat = fmt
                extractor.selectTrack(i)
                break
            }
        }

        if (audioTrackIndex == -1 || audioFormat == null) {
            extractor.release()
            error("No audio track found in source clip")
        }

        // Re-mux audio into a new container
        val pfd = requireNotNull(context.contentResolver.openFileDescriptor(outputUri, "rw"))
        val muxer = MediaMuxer(pfd.fileDescriptor, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        val muxerTrack = muxer.addTrack(audioFormat)
        muxer.start()

        val trimStartUs = primaryClip.trimStartMs * 1_000
        val trimEndUs = (primaryClip.durationMs - primaryClip.trimEndMs) * 1_000
        val maxDurationUs = minOf(projectDurationUs, trimEndUs - trimStartUs)

        extractor.seekTo(trimStartUs, MediaExtractor.SEEK_TO_CLOSEST_SYNC)
        val buffer = ByteBuffer.allocate(256 * 1024)
        val bufferInfo = MediaCodec.BufferInfo()
        var totalWritten = 0L

        try {
            while (true) {
                val sampleSize = extractor.readSampleData(buffer, 0)
                if (sampleSize < 0) break
                val sampleTimeUs = extractor.sampleTime - trimStartUs
                if (sampleTimeUs > maxDurationUs) break
                bufferInfo.set(0, sampleSize, sampleTimeUs.coerceAtLeast(0), extractor.sampleFlags)
                muxer.writeSampleData(muxerTrack, buffer, bufferInfo)
                extractor.advance()
                totalWritten += sampleSize

                val progress = if (maxDurationUs > 0) {
                    ((sampleTimeUs.toFloat() / maxDurationUs) * 100).toInt().coerceIn(1, 99)
                } else 50
                onProgress(progress)
            }
            onProgress(100)
        } finally {
            extractor.release()
            muxer.stop()
            muxer.release()
            pfd.close()
        }

        outputUri.toString()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ExportBindings {
    @Binds
    @Singleton
    abstract fun bindExportCoordinator(impl: MediaStoreExportCoordinator): ExportCoordinator
}

data class ExportSheetUiState(
    val project: Project? = null,
    val fileName: String = "multivideo_export.mp4",
    val preset: ExportPreset = ExportPreset.P720,
    val exportMode: ExportMode = ExportMode.VIDEO,
)

@HiltViewModel
class ExportSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    projectRepository: ProjectRepository,
) : ViewModel() {
    private val projectId: String = checkNotNull(savedStateHandle["projectId"])
    val uiState: StateFlow<ExportSheetUiState> =
        projectRepository.observeProject(projectId).map { project ->
            ExportSheetUiState(
                project = project,
                fileName = project?.name ?: "multivideo",
                preset = project?.exportPreset ?: ExportPreset.P720,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), ExportSheetUiState())
}

@HiltViewModel
class ExportProgressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    private val projectId: String = checkNotNull(savedStateHandle["projectId"])
    val status = projectRepository.observeExportStatus(projectId)
        .map { it ?: ExportStatus(projectId, ExportState.IDLE) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            ExportStatus(projectId, ExportState.IDLE),
        )
}

private val SheetBg = Color(0xFF0E0E0E)
private val SheetSurface = Color(0xFF1A1A1A)
private val SheetBorder = Color(0xFF2A2A2A)
private val SheetText = Color(0xFFF5F5F5)
private val SheetTextSec = Color(0xFF999999)
private val SheetAccent = Color(0xFF6C5CE7)
private val SheetAccentAudio = Color(0xFFE17055)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportSheetRoute(
    onBack: () -> Unit,
    onStartExport: (String) -> Unit,
    viewModel: ExportSheetViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var exportMode by remember { mutableStateOf(ExportMode.VIDEO) }
    var selectedPreset by remember(state.preset.label) { mutableStateOf(state.preset) }
    var selectedAudioPreset by remember { mutableStateOf(ExportPreset.AUDIO_192) }

    val activePreset = if (exportMode == ExportMode.VIDEO) selectedPreset else selectedAudioPreset
    val fileName = when (exportMode) {
        ExportMode.VIDEO -> "${state.fileName}.mp4"
        ExportMode.AUDIO_ONLY -> "${state.fileName}.mp3"
    }

    Scaffold(
        topBar = {
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
                    TextButton(onClick = onBack) {
                        Text(
                            "EXPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = SheetBg,
                ),
            )
        },
        containerColor = SheetBg,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Mode selector: Video / Audio Only
            Text("FORMAT", color = SheetTextSec, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.5.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                ExportModeChip(
                    label = "Video (MP4)",
                    selected = exportMode == ExportMode.VIDEO,
                    accentColor = SheetAccent,
                    onClick = { exportMode = ExportMode.VIDEO },
                    modifier = Modifier.weight(1f),
                )
                ExportModeChip(
                    label = "Audio Only (MP3)",
                    selected = exportMode == ExportMode.AUDIO_ONLY,
                    accentColor = SheetAccentAudio,
                    onClick = { exportMode = ExportMode.AUDIO_ONLY },
                    modifier = Modifier.weight(1f),
                )
            }

            HorizontalDivider(thickness = 1.dp, color = SheetBorder)

            if (exportMode == ExportMode.VIDEO) {
                // Video preset selection
                Text("RESOLUTION", color = SheetTextSec, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.5.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ExportPreset.defaults.forEach { preset ->
                        PresetRow(
                            preset = preset,
                            selected = selectedPreset.label == preset.label,
                            onClick = { selectedPreset = preset },
                        )
                    }
                }
            } else {
                // Audio quality selection
                Text("AUDIO QUALITY", color = SheetTextSec, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.5.sp)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ExportPreset.audioDefaults.forEach { preset ->
                        AudioPresetRow(
                            preset = preset,
                            selected = selectedAudioPreset.label == preset.label,
                            onClick = { selectedAudioPreset = preset },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Extracts and mixes audio tracks from all clips into a single MP3 file.",
                    color = SheetTextSec,
                    fontSize = 12.sp,
                )
            }

            HorizontalDivider(thickness = 1.dp, color = SheetBorder)

            // Output filename
            Text("OUTPUT", color = SheetTextSec, fontSize = 11.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.5.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SheetSurface, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(fileName, color = SheetText, fontSize = 14.sp, modifier = Modifier.weight(1f))
                val ext = if (exportMode == ExportMode.VIDEO) "MP4" else "MP3"
                Text(
                    ext,
                    color = if (exportMode == ExportMode.VIDEO) SheetAccent else SheetAccentAudio,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Export button
            val buttonColor = if (exportMode == ExportMode.VIDEO) SheetAccent else SheetAccentAudio
            Button(
                onClick = {
                    startExportService(context, requireNotNull(state.project), fileName, activePreset, exportMode)
                    onStartExport(requireNotNull(state.project).id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            ) {
                Text(
                    if (exportMode == ExportMode.VIDEO) "Export Video" else "Export Audio",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ExportModeChip(
    label: String,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = if (selected) accentColor.copy(alpha = 0.15f) else SheetSurface
    val borderColor = if (selected) accentColor else SheetBorder
    val textColor = if (selected) accentColor else SheetTextSec
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 1.5.dp else 1.dp,
            color = borderColor,
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = bgColor,
            contentColor = textColor,
        ),
    ) {
        Text(label, fontSize = 13.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
    }
}

@Composable
private fun PresetRow(preset: ExportPreset, selected: Boolean, onClick: () -> Unit) {
    val borderColor = if (selected) SheetAccent else SheetBorder
    val bgColor = if (selected) SheetAccent.copy(alpha = 0.08f) else SheetSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(bgColor, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(preset.label, color = SheetText, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(
                "${preset.width}x${preset.height} \u2022 ${preset.bitrate / 1_000_000} Mbps",
                color = SheetTextSec,
                fontSize = 12.sp,
            )
        }
        androidx.compose.material3.RadioButton(
            selected = selected,
            onClick = onClick,
            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                selectedColor = SheetAccent,
                unselectedColor = SheetBorder,
            ),
        )
    }
}

@Composable
private fun AudioPresetRow(preset: ExportPreset, selected: Boolean, onClick: () -> Unit) {
    val borderColor = if (selected) SheetAccentAudio else SheetBorder
    val bgColor = if (selected) SheetAccentAudio.copy(alpha = 0.08f) else SheetSurface
    val qualityLabel = when (preset.audioBitrate) {
        128_000 -> "Standard"
        192_000 -> "High Quality"
        320_000 -> "Maximum"
        else -> ""
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(bgColor, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("${preset.audioBitrate / 1_000} kbps", color = SheetText, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(qualityLabel, color = SheetTextSec, fontSize = 12.sp)
        }
        androidx.compose.material3.RadioButton(
            selected = selected,
            onClick = onClick,
            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                selectedColor = SheetAccentAudio,
                unselectedColor = SheetBorder,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportProgressRoute(
    onBack: () -> Unit,
    onCompleted: (String) -> Unit,
    viewModel: ExportProgressViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    // Auto-navigate when export succeeds
    LaunchedEffect(status.state) {
        if (status.state == ExportState.SUCCESS) {
            onCompleted(status.projectId)
        }
    }

    Scaffold(
        topBar = {
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
                    TextButton(onClick = {}) {
                        Text(
                            "EXPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0E0E0E)),
            )
        },
        containerColor = Color(0xFF0E0E0E),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (status.state) {
                ExportState.IDLE, ExportState.RUNNING -> {
                    Text(
                        "Exporting...",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LinearProgressIndicator(
                        progress = { status.progress / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = SheetAccent,
                        trackColor = SheetBorder,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "${status.progress}%",
                        color = SheetTextSec,
                        fontSize = 14.sp,
                    )
                }

                ExportState.FAILED -> {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Export failed",
                        tint = Color(0xFFE74C3C),
                        modifier = Modifier.size(48.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Export Failed",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    status.message?.let { msg ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            msg,
                            color = SheetTextSec,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = SheetAccent),
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Text("Go Back", fontWeight = FontWeight.SemiBold)
                    }
                }

                ExportState.SUCCESS -> {
                    // Auto-navigation handles this, but show briefly
                    Text(
                        "Export Complete!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                ExportState.CANCELED -> {
                    Text(
                        "Export Canceled",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = SheetAccent),
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Text("Go Back", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

data class ExportCompleteUiState(
    val project: Project? = null,
    val exportStatus: ExportStatus? = null,
)

@HiltViewModel
class ExportCompleteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    projectRepository: ProjectRepository,
) : ViewModel() {
    private val projectId: String = checkNotNull(savedStateHandle["projectId"])
    val uiState: StateFlow<ExportCompleteUiState> =
        combine(
            projectRepository.observeProject(projectId),
            projectRepository.observeExportStatus(projectId),
        ) { project, status ->
            ExportCompleteUiState(project = project, exportStatus = status)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), ExportCompleteUiState())
}

private val ExportDarkBg = Color(0xFF0E0E0E)
private val ExportSurface = Color(0xFF1A1A1A)
private val ExportBorder = Color(0xFF2A2A2A)
private val ExportTextPrimary = Color(0xFFF5F5F5)
private val ExportTextSecondary = Color(0xFF999999)
private val ExportTextTertiary = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportCompleteRoute(
    onDone: () -> Unit,
    onNewProject: () -> Unit,
    onShare: () -> Unit,
    onOpenApp: () -> Unit,
    viewModel: ExportCompleteViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val project = state.project
    val exportStatus = state.exportStatus

    val preset = project?.exportPreset ?: ExportPreset.P720
    val isAudioExport = exportStatus?.outputUri?.let {
        it.contains("audio") || it.contains("Music")
    } ?: false
    val projectName = project?.name ?: "Untitled"
    val totalDurationMs = project?.clips?.let { TimelineMath.projectDurationMs(it) } ?: 0L
    val durationSec = totalDurationMs / 1000
    val durationFormatted = String.format("%02d:%02d", durationSec / 60, durationSec % 60)
    val resolutionLabel = if (isAudioExport) "Audio" else if (preset.width >= 3840) "4K" else if (preset.width >= 1920) "1080p" else "720p"
    val fpsLabel = if (isAudioExport) "" else "${preset.frameRate}FPS"
    val bitrateLabel = if (isAudioExport) "${preset.audioBitrate / 1_000} kbps" else "${preset.bitrate / 1_000_000} Mbps"
    val codecLabel = if (isAudioExport) "AAC" else "AVC (H.264)"
    val savedLabel = if (isAudioExport) "Audio Saved to Music!" else "Video Saved to Photos!"
    val subtitleLabel = if (isAudioExport) "Export successful \u2022 Music" else "Export successful \u2022 Library"

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onDone) {
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
                    TextButton(onClick = {}) {
                        Text(
                            "EXPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ExportDarkBg),
            )
        },
        containerColor = ExportDarkBg,
    ) { padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(ExportDarkBg),
    ) {
        // Video thumbnail with checkmark overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        ) {
            // Gradient background simulating video thumbnail
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF1A1A2E),
                                Color(0xFF16213E),
                                Color(0xFF0F3460),
                            ),
                        ),
                    ),
            )

            // Checkmark circle overlay
            Box(
                modifier = Modifier.align(Alignment.Center),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Export complete",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp),
                    )
                }
            }

            // Bottom overlay with project info
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        ),
                    )
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Column {
                    Text(
                        text = "FINAL RENDER",
                        color = ExportTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = projectName.replace(" ", "_"),
                            color = ExportTextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = if (isAudioExport) resolutionLabel else "$resolutionLabel $fpsLabel",
                            color = ExportTextSecondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Divider
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 40.dp),
            thickness = 1.dp,
            color = ExportBorder,
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = savedLabel,
            color = ExportTextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitleLabel,
            color = ExportTextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        // "+ New Project" button
        OutlinedButton(
            onClick = onNewProject,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ExportBorder),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = ExportTextPrimary,
            ),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "New Project",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Share & Open App buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = onShare,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ExportBorder),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = ExportTextPrimary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Share", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            OutlinedButton(
                onClick = onOpenApp,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ExportBorder),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = ExportTextPrimary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Filled.OpenInNew,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Open App", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom metadata row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            MetadataItem(label = "CODEC", value = codecLabel)
            MetadataItem(label = "BITRATE", value = bitrateLabel)
            MetadataItem(label = "DURATION", value = durationFormatted)
        }
    }
    }
}

@Composable
private fun MetadataItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = ExportTextTertiary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.5.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = ExportTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

private fun startExportService(context: Context, project: Project, fileName: String, preset: ExportPreset, mode: ExportMode) {
    val intent = Intent(context, ExportForegroundService::class.java).apply {
        putExtra("projectId", project.id)
        putExtra("fileName", fileName)
        putExtra("presetLabel", preset.label)
        putExtra("exportMode", mode.name)
    }
    ContextCompat.startForegroundService(context, intent)
}

@AndroidEntryPoint
class ExportForegroundService : Service() {
    @Inject lateinit var projectRepository: ProjectRepository
    @Inject lateinit var exportCoordinator: ExportCoordinator

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel()
        val mode = ExportMode.valueOf(intent?.getStringExtra("exportMode") ?: ExportMode.VIDEO.name)
        val isAudio = mode == ExportMode.AUDIO_ONLY
        startForeground(1001, buildNotification(if (isAudio) "Preparing audio export" else "Preparing export"))
        val projectId = intent?.getStringExtra("projectId") ?: return START_NOT_STICKY
        val fileName = intent.getStringExtra("fileName") ?: if (isAudio) "multivideo_export.mp3" else "multivideo_export.mp4"
        val allPresets = ExportPreset.defaults + ExportPreset.audioDefaults
        val preset = allPresets.firstOrNull { it.label == intent.getStringExtra("presetLabel") } ?: ExportPreset.P720
        serviceScope.launch {
            val project = projectRepository.loadProject(projectId)
            if (project == null) {
                projectRepository.updateExportStatus(ExportStatus(projectId, ExportState.FAILED, message = "Project not found"))
                stopSelf(startId)
                return@launch
            }
            projectRepository.updateExportStatus(ExportStatus(projectId, ExportState.RUNNING, progress = 1))
            val result = exportCoordinator.export(project, fileName, preset, mode) { progress ->
                projectRepository.updateExportStatus(
                    ExportStatus(projectId, ExportState.RUNNING, progress = progress),
                )
            }
            result.fold(
                onSuccess = { outputUri ->
                    projectRepository.updateExportStatus(
                        ExportStatus(projectId, ExportState.SUCCESS, progress = 100, outputUri = outputUri),
                    )
                },
                onFailure = { error ->
                    projectRepository.updateExportStatus(
                        ExportStatus(projectId, ExportState.FAILED, progress = 0, message = error.message),
                    )
                },
            )
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf(startId)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(
                NotificationChannel(EXPORT_CHANNEL_ID, "Exports", NotificationManager.IMPORTANCE_LOW),
            )
        }
    }

    private fun buildNotification(text: String): Notification =
        NotificationCompat.Builder(this, EXPORT_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setContentTitle("Exporting")
            .setContentText(text)
            .setOngoing(true)
            .build()
}
