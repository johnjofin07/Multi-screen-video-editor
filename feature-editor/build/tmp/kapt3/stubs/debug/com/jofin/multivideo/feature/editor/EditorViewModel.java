package com.jofin.multivideo.feature.editor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001b\u001a\u00020\u0014H\u0014J\u000e\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u0014J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0017J\u000e\u0010\"\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020&0%J\u0010\u0010\'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020)H\u0002J\u000e\u0010*\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rJ)\u0010+\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0017\u0010,\u001a\u0013\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.0-\u00a2\u0006\u0002\b/H\u0002J\u0016\u00100\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0006\u00101\u001a\u00020\u0017J\u0016\u00102\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0006\u00103\u001a\u00020\u0017J\u0016\u00104\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\r2\u0006\u00105\u001a\u000206R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00067"}, d2 = {"Lcom/jofin/multivideo/feature/editor/EditorViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "projectRepository", "Lcom/jofin/multivideo/domain/repository/ProjectRepository;", "previewSessionManager", "Lcom/jofin/multivideo/core/playback/PreviewSessionManager;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/jofin/multivideo/domain/repository/ProjectRepository;Lcom/jofin/multivideo/core/playback/PreviewSessionManager;)V", "previewStateFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/jofin/multivideo/domain/model/PreviewState;", "projectId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/jofin/multivideo/feature/editor/EditorUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "adjustOffset", "", "clipId", "deltaMs", "", "onAudioModeSelected", "audioMode", "Lcom/jofin/multivideo/domain/model/AudioMode;", "onCleared", "onLayoutSelected", "layoutType", "Lcom/jofin/multivideo/domain/model/LayoutType;", "onPlayPause", "onSeek", "projectTimeMs", "onSelectClip", "onSelectedAudioClip", "playerMap", "", "Landroidx/media3/exoplayer/ExoPlayer;", "saveProject", "project", "Lcom/jofin/multivideo/domain/model/Project;", "toggleMute", "updateClip", "transform", "Lkotlin/Function1;", "Lcom/jofin/multivideo/domain/model/ClipTrack;", "Lkotlin/ExtensionFunctionType;", "updateTrimEnd", "trimEndMs", "updateTrimStart", "trimStartMs", "updateVolume", "volume", "", "feature-editor_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EditorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.domain.repository.ProjectRepository projectRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.core.playback.PreviewSessionManager previewSessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String projectId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.jofin.multivideo.domain.model.PreviewState> previewStateFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.editor.EditorUiState> uiState = null;
    
    @javax.inject.Inject()
    public EditorViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.repository.ProjectRepository projectRepository, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.core.playback.PreviewSessionManager previewSessionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.jofin.multivideo.feature.editor.EditorUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, androidx.media3.exoplayer.ExoPlayer> playerMap() {
        return null;
    }
    
    public final void onPlayPause() {
    }
    
    public final void onSeek(long projectTimeMs) {
    }
    
    public final void onSelectClip(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId) {
    }
    
    public final void onLayoutSelected(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.LayoutType layoutType) {
    }
    
    public final void onAudioModeSelected(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.AudioMode audioMode) {
    }
    
    public final void onSelectedAudioClip(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId) {
    }
    
    public final void adjustOffset(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId, long deltaMs) {
    }
    
    public final void updateTrimStart(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId, long trimStartMs) {
    }
    
    public final void updateTrimEnd(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId, long trimEndMs) {
    }
    
    public final void updateVolume(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId, float volume) {
    }
    
    public final void toggleMute(@org.jetbrains.annotations.NotNull()
    java.lang.String clipId) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    private final void updateClip(java.lang.String clipId, kotlin.jvm.functions.Function1<? super com.jofin.multivideo.domain.model.ClipTrack, com.jofin.multivideo.domain.model.ClipTrack> transform) {
    }
    
    private final void saveProject(com.jofin.multivideo.domain.model.Project project) {
    }
}