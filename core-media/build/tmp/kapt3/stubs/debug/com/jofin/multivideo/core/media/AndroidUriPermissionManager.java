package com.jofin.multivideo.core.media;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/jofin/multivideo/core/media/AndroidUriPermissionManager;", "Lcom/jofin/multivideo/core/media/UriPermissionManager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "takeReadPermission", "", "uri", "Landroid/net/Uri;", "core-media_debug"})
public final class AndroidUriPermissionManager implements com.jofin.multivideo.core.media.UriPermissionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public AndroidUriPermissionManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    public void takeReadPermission(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
}