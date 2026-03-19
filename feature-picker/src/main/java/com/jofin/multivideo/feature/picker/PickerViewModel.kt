package com.jofin.multivideo.feature.picker

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jofin.multivideo.core.media.MediaInputValidator
import com.jofin.multivideo.core.media.MediaMetadataReader
import com.jofin.multivideo.core.media.UriPermissionManager
import com.jofin.multivideo.domain.model.ProjectSummary
import com.jofin.multivideo.domain.model.VideoMetadata
import com.jofin.multivideo.domain.repository.ProjectRepository
import com.jofin.multivideo.domain.usecase.CreateProjectFromMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PickerUiState(
    val isLoading: Boolean = false,
    val selectedMetadata: List<VideoMetadata> = emptyList(),
    val errorMessage: String? = null,
    val createdProjectId: String? = null,
)

@HiltViewModel
class PickerViewModel @Inject constructor(
    private val metadataReader: MediaMetadataReader,
    private val permissionManager: UriPermissionManager,
    private val createProjectFromMediaUseCase: CreateProjectFromMediaUseCase,
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PickerUiState())
    val uiState: StateFlow<PickerUiState> = _uiState.asStateFlow()

    val recentProjects: StateFlow<List<ProjectSummary>> =
        projectRepository.observeRecentProjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), emptyList())

    fun onPickerResult(uris: List<Uri>) {
        val selectionError = MediaInputValidator.validateSelectionCount(uris.size)
        if (selectionError != null) {
            _uiState.value = PickerUiState(errorMessage = selectionError)
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val metadata = buildList {
                uris.forEach { uri ->
                    permissionManager.takeReadPermission(uri)
                    metadataReader.read(uri)
                        .onSuccess { item ->
                            MediaInputValidator.validateMetadata(item)?.let { error ->
                                _uiState.value = PickerUiState(errorMessage = error)
                                return@launch
                            }
                            add(item)
                        }
                        .onFailure {
                            _uiState.value = PickerUiState(errorMessage = "Failed to read ${uri.lastPathSegment}.")
                            return@launch
                        }
                }
            }
            val project = createProjectFromMediaUseCase(metadata)
            projectRepository.createProject(project)
            _uiState.value = PickerUiState(
                isLoading = false,
                selectedMetadata = metadata,
                createdProjectId = project.id,
            )
        }
    }

    fun consumeCreatedProject() {
        _uiState.value = _uiState.value.copy(createdProjectId = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
