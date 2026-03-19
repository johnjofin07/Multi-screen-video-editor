package com.jofin.multivideo.feature.export;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/jofin/multivideo/feature/export/ExportSheetViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "projectRepository", "Lcom/jofin/multivideo/domain/repository/ProjectRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/jofin/multivideo/domain/repository/ProjectRepository;)V", "projectId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/jofin/multivideo/feature/export/ExportSheetUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "feature-export_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExportSheetViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String projectId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.export.ExportSheetUiState> uiState = null;
    
    @javax.inject.Inject()
    public ExportSheetViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.repository.ProjectRepository projectRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.export.ExportSheetUiState> getUiState() {
        return null;
    }
}