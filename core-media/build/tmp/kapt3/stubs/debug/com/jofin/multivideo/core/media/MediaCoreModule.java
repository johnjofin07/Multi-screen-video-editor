package com.jofin.multivideo.core.media;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'\u00a8\u0006\n"}, d2 = {"Lcom/jofin/multivideo/core/media/MediaCoreModule;", "", "()V", "bindMetadataReader", "Lcom/jofin/multivideo/core/media/MediaMetadataReader;", "impl", "Lcom/jofin/multivideo/core/media/AndroidMediaMetadataReader;", "bindUriPermissionManager", "Lcom/jofin/multivideo/core/media/UriPermissionManager;", "Lcom/jofin/multivideo/core/media/AndroidUriPermissionManager;", "core-media_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class MediaCoreModule {
    
    public MediaCoreModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.jofin.multivideo.core.media.MediaMetadataReader bindMetadataReader(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.core.media.AndroidMediaMetadataReader impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.jofin.multivideo.core.media.UriPermissionManager bindUriPermissionManager(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.core.media.AndroidUriPermissionManager impl);
}