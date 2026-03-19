package com.jofin.multivideo.feature.picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jofin.multivideo.domain.model.ProjectSummary

@Composable
fun HomeRoute(
    onNewProject: () -> Unit,
    onOpenProject: (String) -> Unit,
    viewModel: PickerViewModel = hiltViewModel(),
) {
    val recentProjects by viewModel.recentProjects.collectAsStateWithLifecycle()
    HomeScreen(recentProjects = recentProjects, onNewProject = onNewProject, onOpenProject = onOpenProject)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    recentProjects: List<ProjectSummary>,
    onNewProject: () -> Unit,
    onOpenProject: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Multi Video Editor") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(onClick = onNewProject, modifier = Modifier.fillMaxWidth()) {
                Text("New Project")
            }
            Text("Recent Projects", style = MaterialTheme.typography.titleMedium)
            if (recentProjects.isEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Create your first project to start combining videos.",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(recentProjects, key = { it.id }) { project ->
                        Card(onClick = { onOpenProject(project.id) }, modifier = Modifier.fillMaxWidth()) {
                            ListItem(
                                headlineContent = { Text(project.name) },
                                supportingContent = { Text("${project.layoutType} • ${project.clipCount} clips") },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PickerRoute(
    onBack: () -> Unit,
    onProjectCreated: (String) -> Unit,
    viewModel: PickerViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4),
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) viewModel.onPickerResult(uris)
    }
    val openDocumentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments(),
    ) { uris ->
        if (uris.isNotEmpty()) viewModel.onPickerResult(uris)
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
    LaunchedEffect(state.createdProjectId) {
        state.createdProjectId?.let {
            onProjectCreated(it)
            viewModel.consumeCreatedProject()
        }
    }

    PickerScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onBack = onBack,
        onOpenPhotoPicker = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        },
        onFallbackPicker = { openDocumentLauncher.launch(arrayOf("video/*")) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickerScreen(
    state: PickerUiState,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onOpenPhotoPicker: () -> Unit,
    onFallbackPicker: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select videos") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text("Choose 2 to 4 local videos", style = MaterialTheme.typography.titleMedium)
            }
            item {
                Button(onClick = onOpenPhotoPicker, modifier = Modifier.fillMaxWidth()) {
                    Text("Open video picker")
                }
            }
            item {
                TextButton(onClick = onFallbackPicker) {
                    Text("Use document picker fallback")
                }
            }
            if (state.isLoading) {
                item { Text("Loading selected videos...") }
            }
            items(state.selectedMetadata, key = { it.uri }) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(item.displayName) },
                        supportingContent = { Text("${item.durationMs} ms • ${item.width}x${item.height}") },
                    )
                }
            }
        }
    }
}
