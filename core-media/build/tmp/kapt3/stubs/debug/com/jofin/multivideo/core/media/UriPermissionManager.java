package com.jofin.multivideo.core.media;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/jofin/multivideo/core/media/UriPermissionManager;", "", "takeReadPermission", "", "uri", "Landroid/net/Uri;", "core-media_debug"})
public abstract interface UriPermissionManager {
    
    public abstract void takeReadPermission(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri);
}