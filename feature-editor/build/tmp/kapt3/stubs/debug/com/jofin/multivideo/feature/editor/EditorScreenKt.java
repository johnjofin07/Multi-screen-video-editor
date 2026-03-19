package com.jofin.multivideo.feature.editor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u001a\u0084\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a4\u0010\u0010\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001a$\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001b0\u001aH\u0003\u00a8\u0006\u001c"}, d2 = {"ClipInspector", "", "clip", "Lcom/jofin/multivideo/domain/model/ClipTrack;", "isSelectedAudio", "", "onSelectAudio", "Lkotlin/Function0;", "onOffsetAdjust", "Lkotlin/Function1;", "", "onTrimStart", "", "onTrimEnd", "onVolume", "onMute", "EditorRoute", "onBack", "onExport", "", "viewModel", "Lcom/jofin/multivideo/feature/editor/EditorViewModel;", "PreviewCanvas", "project", "Lcom/jofin/multivideo/domain/model/Project;", "players", "", "Landroidx/media3/exoplayer/ExoPlayer;", "feature-editor_debug"})
public final class EditorScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void EditorRoute(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onExport, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.feature.editor.EditorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PreviewCanvas(com.jofin.multivideo.domain.model.Project project, java.util.Map<java.lang.String, ? extends androidx.media3.exoplayer.ExoPlayer> players) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ClipInspector(com.jofin.multivideo.domain.model.ClipTrack clip, boolean isSelectedAudio, kotlin.jvm.functions.Function0<kotlin.Unit> onSelectAudio, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onOffsetAdjust, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onTrimStart, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onTrimEnd, kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onVolume, kotlin.jvm.functions.Function0<kotlin.Unit> onMute) {
    }
}