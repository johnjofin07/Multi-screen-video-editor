package com.jofin.multivideo.data.project.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\n0\u000fH\'J$\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\r2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0097@\u00a2\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\u00020\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\u001f"}, d2 = {"Lcom/jofin/multivideo/data/project/db/ProjectDao;", "", "countClips", "", "projectId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteClipsForProject", "", "getClips", "", "Lcom/jofin/multivideo/data/project/model/ClipTrackEntity;", "getProjectEntity", "Lcom/jofin/multivideo/data/project/model/ProjectEntity;", "observeClips", "Lkotlinx/coroutines/flow/Flow;", "observeExportStatus", "Lcom/jofin/multivideo/data/project/model/ExportStatusEntity;", "observeProjectEntity", "observeProjects", "replaceProject", "project", "clips", "(Lcom/jofin/multivideo/data/project/model/ProjectEntity;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertClips", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertExportStatus", "status", "(Lcom/jofin/multivideo/data/project/model/ExportStatusEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsertProject", "(Lcom/jofin/multivideo/data/project/model/ProjectEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data-project_debug"})
@androidx.room.Dao()
public abstract interface ProjectDao {
    
    @androidx.room.Query(value = "SELECT * FROM projects ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.jofin.multivideo.data.project.model.ProjectEntity>> observeProjects();
    
    @androidx.room.Query(value = "SELECT * FROM projects WHERE id = :projectId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.jofin.multivideo.data.project.model.ProjectEntity> observeProjectEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId);
    
    @androidx.room.Query(value = "SELECT * FROM clips WHERE projectId = :projectId ORDER BY slotIndex ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.jofin.multivideo.data.project.model.ClipTrackEntity>> observeClips(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId);
    
    @androidx.room.Query(value = "SELECT * FROM export_status WHERE projectId = :projectId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.jofin.multivideo.data.project.model.ExportStatusEntity> observeExportStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId);
    
    @androidx.room.Query(value = "SELECT * FROM projects WHERE id = :projectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProjectEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.jofin.multivideo.data.project.model.ProjectEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM clips WHERE projectId = :projectId ORDER BY slotIndex ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClips(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.jofin.multivideo.data.project.model.ClipTrackEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM clips WHERE projectId = :projectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countClips(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsertProject(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.data.project.model.ProjectEntity project, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsertClips(@org.jetbrains.annotations.NotNull()
    java.util.List<com.jofin.multivideo.data.project.model.ClipTrackEntity> clips, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM clips WHERE projectId = :projectId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteClipsForProject(@org.jetbrains.annotations.NotNull()
    java.lang.String projectId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsertExportStatus(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.data.project.model.ExportStatusEntity status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object replaceProject(@org.jetbrains.annotations.NotNull()
    com.jofin.multivideo.data.project.model.ProjectEntity project, @org.jetbrains.annotations.NotNull()
    java.util.List<com.jofin.multivideo.data.project.model.ClipTrackEntity> clips, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object replaceProject(@org.jetbrains.annotations.NotNull()
        com.jofin.multivideo.data.project.db.ProjectDao $this, @org.jetbrains.annotations.NotNull()
        com.jofin.multivideo.data.project.model.ProjectEntity project, @org.jetbrains.annotations.NotNull()
        java.util.List<com.jofin.multivideo.data.project.model.ClipTrackEntity> clips, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}