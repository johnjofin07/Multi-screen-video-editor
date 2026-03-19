package com.jofin.multivideo.core.playback;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\u0011\u001a\u00020\u0010J\u0010\u0010\u0012\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\tJ\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0014J\u0006\u0010\u0015\u001a\u00020\u0010J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\tH\u0002J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0013\u001a\u0004\u0018\u00010\tJ\b\u0010\u001b\u001a\u00020\u0010H\u0002J\u0012\u0010\u001c\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/jofin/multivideo/core/playback/PreviewSessionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "driftJob", "Lkotlinx/coroutines/Job;", "players", "Ljava/util/LinkedHashMap;", "", "Landroidx/media3/exoplayer/ExoPlayer;", "project", "Lcom/jofin/multivideo/domain/model/Project;", "sessionScope", "Lkotlinx/coroutines/CoroutineScope;", "attachProject", "", "pause", "play", "selectedAudioClipId", "", "releaseAll", "removePlayer", "id", "seekTo", "projectTimeMs", "", "startDriftCorrection", "updateAudibleTrack", "core-playback_debug"})
public final class PreviewSessionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope sessionScope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashMap<java.lang.String, androidx.media3.exoplayer.ExoPlayer> players = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job driftJob;
    @org.jetbrains.annotations.Nullable()
    private com.jofin.multivideo.domain.model.Project project;
    
    @javax.inject.Inject()
    public PreviewSessionManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void attachProject(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.Project project) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, androidx.media3.exoplayer.ExoPlayer> players() {
        return null;
    }
    
    public final void play(@org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioClipId) {
    }
    
    public final void pause() {
    }
    
    public final void seekTo(long projectTimeMs, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioClipId) {
    }
    
    public final void releaseAll() {
    }
    
    private final void updateAudibleTrack(java.lang.String selectedAudioClipId) {
    }
    
    private final void startDriftCorrection() {
    }
    
    private final void removePlayer(java.lang.String id) {
    }
}