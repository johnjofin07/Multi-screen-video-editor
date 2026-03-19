package com.jofin.multivideo.data.project.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/jofin/multivideo/data/project/db/ProjectTypeConverters;", "", "()V", "fromAudioMode", "", "value", "Lcom/jofin/multivideo/domain/model/AudioMode;", "fromLayoutType", "Lcom/jofin/multivideo/domain/model/LayoutType;", "toAudioMode", "toLayoutType", "data-project_debug"})
public final class ProjectTypeConverters {
    
    public ProjectTypeConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromLayoutType(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.LayoutType value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.LayoutType toLayoutType(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromAudioMode(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.AudioMode value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.AudioMode toAudioMode(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
}