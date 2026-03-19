package com.jofin.multivideo.feature.export;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0007\u001a4\u0010\u0006\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a4\u0010\f\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00030\t2\b\b\u0002\u0010\n\u001a\u00020\u000eH\u0007\u001a(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"EXPORT_CHANNEL_ID", "", "ExportCompleteRoute", "", "onDone", "Lkotlin/Function0;", "ExportProgressRoute", "onBack", "onCompleted", "Lkotlin/Function1;", "viewModel", "Lcom/jofin/multivideo/feature/export/ExportProgressViewModel;", "ExportSheetRoute", "onStartExport", "Lcom/jofin/multivideo/feature/export/ExportSheetViewModel;", "startExportService", "context", "Landroid/content/Context;", "project", "Lcom/jofin/multivideo/domain/model/Project;", "fileName", "preset", "Lcom/jofin/multivideo/domain/model/ExportPreset;", "feature-export_debug"})
public final class ExportFeatureKt {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXPORT_CHANNEL_ID = "multivideo_export";
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExportSheetRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStartExport, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.export.ExportSheetViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExportProgressRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCompleted, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.export.ExportProgressViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExportCompleteRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    private static final void startExportService(android.content.Context context, com.jofin.multivideo.domain.model.Project project, java.lang.String fileName, com.jofin.multivideo.domain.model.ExportPreset preset) {
    }
}