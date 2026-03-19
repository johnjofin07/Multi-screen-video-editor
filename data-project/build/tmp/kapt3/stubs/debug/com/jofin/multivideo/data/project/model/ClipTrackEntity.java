package com.jofin.multivideo.data.project.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b2\b\u0087\b\u0018\u00002\u00020\u0001B\u008d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\b\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\n\u0012\u0006\u0010\u0015\u001a\u00020\n\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0018J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\bH\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\t\u00102\u001a\u00020\u0011H\u00c6\u0003J\t\u00103\u001a\u00020\u0013H\u00c6\u0003J\t\u00104\u001a\u00020\nH\u00c6\u0003J\t\u00105\u001a\u00020\nH\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0013H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\bH\u00c6\u0003J\t\u0010<\u001a\u00020\nH\u00c6\u0003J\t\u0010=\u001a\u00020\nH\u00c6\u0003J\t\u0010>\u001a\u00020\nH\u00c6\u0003J\t\u0010?\u001a\u00020\bH\u00c6\u0003J\u00b3\u0001\u0010@\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u0013H\u00c6\u0001J\u0013\u0010A\u001a\u00020\u00132\b\u0010B\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010C\u001a\u00020\nH\u00d6\u0001J\t\u0010D\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0017\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0011\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0011\u0010\u0014\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001cR\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001cR\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010\u0015\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 \u00a8\u0006E"}, d2 = {"Lcom/jofin/multivideo/data/project/model/ClipTrackEntity;", "", "id", "", "projectId", "uri", "displayName", "durationMs", "", "width", "", "height", "rotationDegrees", "trimStartMs", "trimEndMs", "startOffsetMs", "volume", "", "muted", "", "slotIndex", "zIndex", "mimeType", "hasAudio", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JIIIJJJFZIILjava/lang/String;Z)V", "getDisplayName", "()Ljava/lang/String;", "getDurationMs", "()J", "getHasAudio", "()Z", "getHeight", "()I", "getId", "getMimeType", "getMuted", "getProjectId", "getRotationDegrees", "getSlotIndex", "getStartOffsetMs", "getTrimEndMs", "getTrimStartMs", "getUri", "getVolume", "()F", "getWidth", "getZIndex", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "data-project_debug"})
@androidx.room.Entity(tableName = "clips", foreignKeys = {@androidx.room.ForeignKey(entity = com.jofin.multivideo.data.project.model.ProjectEntity.class, parentColumns = {"id"}, childColumns = {"projectId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"projectId"})})
public final class ClipTrackEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String projectId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String uri = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    private final long durationMs = 0L;
    private final int width = 0;
    private final int height = 0;
    private final int rotationDegrees = 0;
    private final long trimStartMs = 0L;
    private final long trimEndMs = 0L;
    private final long startOffsetMs = 0L;
    private final float volume = 0.0F;
    private final boolean muted = false;
    private final int slotIndex = 0;
    private final int zIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mimeType = null;
    private final boolean hasAudio = false;
    
    public ClipTrackEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, long durationMs, int width, int height, int rotationDegrees, long trimStartMs, long trimEndMs, long startOffsetMs, float volume, boolean muted, int slotIndex, int zIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, boolean hasAudio) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProjectId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    public final long getDurationMs() {
        return 0L;
    }
    
    public final int getWidth() {
        return 0;
    }
    
    public final int getHeight() {
        return 0;
    }
    
    public final int getRotationDegrees() {
        return 0;
    }
    
    public final long getTrimStartMs() {
        return 0L;
    }
    
    public final long getTrimEndMs() {
        return 0L;
    }
    
    public final long getStartOffsetMs() {
        return 0L;
    }
    
    public final float getVolume() {
        return 0.0F;
    }
    
    public final boolean getMuted() {
        return false;
    }
    
    public final int getSlotIndex() {
        return 0;
    }
    
    public final int getZIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMimeType() {
        return null;
    }
    
    public final boolean getHasAudio() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final long component10() {
        return 0L;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final float component12() {
        return 0.0F;
    }
    
    public final boolean component13() {
        return false;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final int component15() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.jofin.multivideo.data.project.model.ClipTrackEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, long durationMs, int width, int height, int rotationDegrees, long trimStartMs, long trimEndMs, long startOffsetMs, float volume, boolean muted, int slotIndex, int zIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String mimeType, boolean hasAudio) {
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