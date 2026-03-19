package com.jofin.multivideo.data.project.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u007f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\u000b\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0006\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u000bH\u00c6\u0003J\t\u0010-\u001a\u00020\u000bH\u00c6\u0003J\t\u0010.\u001a\u00020\u0011H\u00c6\u0003J\t\u0010/\u001a\u00020\u0013H\u00c6\u0003J\t\u00100\u001a\u00020\u0006H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0006H\u00c6\u0003J\t\u00104\u001a\u00020\u0006H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u000bH\u00c6\u0003J\t\u00108\u001a\u00020\u000bH\u00c6\u0003J\t\u00109\u001a\u00020\u000bH\u00c6\u0003J\u00a1\u0001\u0010:\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00062\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010>\u001a\u00020\u000bH\u00d6\u0001J\t\u0010?\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0014\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\r\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\"R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001a\u00a8\u0006@"}, d2 = {"Lcom/jofin/multivideo/data/project/model/ProjectEntity;", "", "id", "", "name", "createdAt", "", "updatedAt", "outputAspectRatio", "exportPresetLabel", "exportWidth", "", "exportHeight", "exportBitrate", "exportFrameRate", "exportAudioBitrate", "layoutType", "Lcom/jofin/multivideo/domain/model/LayoutType;", "audioMode", "Lcom/jofin/multivideo/domain/model/AudioMode;", "backgroundColor", "selectedAudioClipId", "(Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;Ljava/lang/String;IIIIILcom/jofin/multivideo/domain/model/LayoutType;Lcom/jofin/multivideo/domain/model/AudioMode;JLjava/lang/String;)V", "getAudioMode", "()Lcom/jofin/multivideo/domain/model/AudioMode;", "getBackgroundColor", "()J", "getCreatedAt", "getExportAudioBitrate", "()I", "getExportBitrate", "getExportFrameRate", "getExportHeight", "getExportPresetLabel", "()Ljava/lang/String;", "getExportWidth", "getId", "getLayoutType", "()Lcom/jofin/multivideo/domain/model/LayoutType;", "getName", "getOutputAspectRatio", "getSelectedAudioClipId", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "data-project_debug"})
@androidx.room.Entity(tableName = "projects")
public final class ProjectEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String outputAspectRatio = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String exportPresetLabel = null;
    private final int exportWidth = 0;
    private final int exportHeight = 0;
    private final int exportBitrate = 0;
    private final int exportFrameRate = 0;
    private final int exportAudioBitrate = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.domain.model.LayoutType layoutType = null;
    @org.jetbrains.annotations.NotNull()
    private final com.jofin.multivideo.domain.model.AudioMode audioMode = null;
    private final long backgroundColor = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedAudioClipId = null;
    
    public ProjectEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, long createdAt, long updatedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String outputAspectRatio, @org.jetbrains.annotations.NotNull()
    java.lang.String exportPresetLabel, int exportWidth, int exportHeight, int exportBitrate, int exportFrameRate, int exportAudioBitrate, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.LayoutType layoutType, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.AudioMode audioMode, long backgroundColor, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioClipId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOutputAspectRatio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getExportPresetLabel() {
        return null;
    }
    
    public final int getExportWidth() {
        return 0;
    }
    
    public final int getExportHeight() {
        return 0;
    }
    
    public final int getExportBitrate() {
        return 0;
    }
    
    public final int getExportFrameRate() {
        return 0;
    }
    
    public final int getExportAudioBitrate() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.LayoutType getLayoutType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.AudioMode getAudioMode() {
        return null;
    }
    
    public final long getBackgroundColor() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedAudioClipId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.LayoutType component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.domain.model.AudioMode component13() {
        return null;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.data.project.model.ProjectEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, long createdAt, long updatedAt, @org.jetbrains.annotations.NotNull()
    java.lang.String outputAspectRatio, @org.jetbrains.annotations.NotNull()
    java.lang.String exportPresetLabel, int exportWidth, int exportHeight, int exportBitrate, int exportFrameRate, int exportAudioBitrate, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.LayoutType layoutType, @org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.domain.model.AudioMode audioMode, long backgroundColor, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioClipId) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}