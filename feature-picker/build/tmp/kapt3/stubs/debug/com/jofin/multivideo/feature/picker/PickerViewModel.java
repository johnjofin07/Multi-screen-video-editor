package com.jofin.multivideo.feature.picker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u0014\u0010\u0019\u001a\u00020\u00172\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0010R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006\u001c"}, d2 = {"Lcom/jofin/multivideo/feature/picker/PickerViewModel;", "Landroidx/lifecycle/ViewModel;", "metadataReader", "Lcom/jofin/multivideo/core/media/MediaMetadataReader;", "permissionManager", "Lcom/jofin/multivideo/core/media/UriPermissionManager;", "createProjectFromMediaUseCase", "Lcom/jofin/multivideo/domain/usecase/CreateProjectFromMediaUseCase;", "projectRepository", "Lcom/jofin/multivideo/domain/repository/ProjectRepository;", "(Lcom/jofin/multivideo/core/media/MediaMetadataReader;Lcom/jofin/multivideo/core/media/UriPermissionManager;Lcom/jofin/multivideo/domain/usecase/CreateProjectFromMediaUseCase;Lcom/jofin/multivideo/domain/repository/ProjectRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/jofin/multivideo/feature/picker/PickerUiState;", "recentProjects", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/jofin/multivideo/domain/model/ProjectSummary;", "getRecentProjects", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "clearError", "", "consumeCreatedProject", "onPickerResult", "uris", "Landroid/net/Uri;", "feature-picker_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PickerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.core.media.MediaMetadataReader metadataReader = null;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.core.media.UriPermissionManager permissionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.domain.usecase.CreateProjectFromMediaUseCase createProjectFromMediaUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.domain.repository.ProjectRepository projectRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.jofin.multivideo.feature.picker.PickerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.picker.PickerUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.jofin.multivideo.domain.model.ProjectSummary>> recentProjects = null;
    
    @javax.inject.Inject()
    public PickerViewModel(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.core.media.MediaMetadataReader metadataReader, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.core.media.UriPermissionManager permissionManager, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.usecase.CreateProjectFromMediaUseCase createProjectFromMediaUseCase, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.repository.ProjectRepository projectRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.picker.PickerUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.jofin.multivideo.domain.model.ProjectSummary>> getRecentProjects() {
        return null;
    }
    
    public final void onPickerResult(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris) {
    }
    
    public final void consumeCreatedProject() {
    }
    
    public final void clearError() {
    }
}