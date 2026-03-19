package com.jofin.multivideo.feature.picker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a4\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a8\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a4\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001aB\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u00a8\u0006\u0017"}, d2 = {"HomeRoute", "", "onNewProject", "Lkotlin/Function0;", "onOpenProject", "Lkotlin/Function1;", "", "viewModel", "Lcom/jofin/multivideo/feature/picker/PickerViewModel;", "HomeScreen", "recentProjects", "", "Lcom/jofin/multivideo/domain/model/ProjectSummary;", "PickerRoute", "onBack", "onProjectCreated", "PickerScreen", "state", "Lcom/jofin/multivideo/feature/picker/PickerUiState;", "snackbarHostState", "Landroidx/compose/material3/SnackbarHostState;", "onOpenPhotoPicker", "onFallbackPicker", "feature-picker_debug"})
public final class PickerScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HomeRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNewProject, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenProject, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.picker.PickerViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void HomeScreen(java.util.List<com.jofin.multivideo.domain.model.ProjectSummary> recentProjects, kotlin.jvm.functions.Function0<kotlin.Unit> onNewProject, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenProject) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PickerRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onProjectCreated, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.picker.PickerViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void PickerScreen(com.jofin.multivideo.feature.picker.PickerUiState state, androidx.compose.material3.SnackbarHostState snackbarHostState, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenPhotoPicker, kotlin.jvm.functions.Function0<kotlin.Unit> onFallbackPicker) {
    }
}