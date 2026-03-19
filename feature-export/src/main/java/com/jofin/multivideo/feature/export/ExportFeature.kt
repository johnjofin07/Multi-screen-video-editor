package com.jofin.multivideo.feature.export

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

const val EXPORT_CHANNEL_ID = "multivideo_export"

interface ExportCoordinator {
    suspend fun export(project: Project, fileName: String, preset: ExportPreset): Result<String>
}

class MediaStoreExportCoordinator @Inject constructor(
    @ApplicationContext private val context: Context,
) : ExportCoordinator {
    override suspend fun export(project: Project, fileName: String, preset: ExportPreset): Result<String> = runCatching {
        delay(1_500L)
        val values = android.content.ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/MultiVideoEditor")
        }
        val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val uri = requireNotNull(context.contentResolver.insert(collection, values))
        context.contentResolver.openOutputStream(uri)?.use { output ->
            val firstClip = project.clips.first()
            context.contentResolver.openInputStream(Uri.parse(firstClip.uri))?.use { input ->
                input.copyTo(output)
            }
        }
        uri.toString()
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
                fileName = "${project?.name ?: "multivideo"}.mp4",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportSheetRoute(
    onBack: () -> Unit,
    onStartExport: (String) -> Unit,
    viewModel: ExportSheetViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedPreset by remember(state.preset.label) { mutableStateOf(state.preset) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Export") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Filename: ${state.fileName}")
            Text("Preset")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ExportPreset.defaults.forEach { preset ->
                    FilterChip(
                        selected = selectedPreset.label == preset.label,
                        onClick = { selectedPreset = preset },
                        label = { Text(preset.label) },
                    )
                }
            }
            Button(
                onClick = {
                    startExportService(context, requireNotNull(state.project), state.fileName, selectedPreset)
                    onStartExport(requireNotNull(state.project).id)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Start export")
            }
        }
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Export progress") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(progress = { status.progress / 100f })
            Text("State: ${status.state}")
            Text("Progress: ${status.progress}%")
            status.outputUri?.let {
                Button(onClick = { onCompleted(status.projectId) }) {
                    Text("Continue")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportCompleteRoute(onDone: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Export complete") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Exported video saved to your media library.")
            Button(onClick = onDone) {
                Text("Done")
            }
        }
    }
}

private fun startExportService(context: Context, project: Project, fileName: String, preset: ExportPreset) {
    val intent = Intent(context, ExportForegroundService::class.java).apply {
        putExtra("projectId", project.id)
        putExtra("fileName", fileName)
        putExtra("presetLabel", preset.label)
    }
    ContextCompat.startForegroundService(context, intent)
}

@AndroidEntryPoint
class ExportForegroundService : Service() {
    @Inject lateinit var projectRepository: ProjectRepository
    @Inject lateinit var exportCoordinator: ExportCoordinator

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createChannel()
        startForeground(1001, buildNotification("Preparing export"))
        val projectId = intent?.getStringExtra("projectId") ?: return START_NOT_STICKY
        val fileName = intent.getStringExtra("fileName") ?: "multivideo_export.mp4"
        val preset = ExportPreset.defaults.firstOrNull { it.label == intent.getStringExtra("presetLabel") } ?: ExportPreset.P720
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            val project = projectRepository.loadProject(projectId)
            if (project == null) {
                projectRepository.updateExportStatus(ExportStatus(projectId, ExportState.FAILED, message = "Project not found"))
                stopSelf(startId)
                return@launch
            }
            projectRepository.updateExportStatus(ExportStatus(projectId, ExportState.RUNNING, progress = 10))
            val result = exportCoordinator.export(project, fileName, preset)
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
            .setContentTitle("Exporting video")
            .setContentText(text)
            .setOngoing(true)
            .build()
}
