package com.jofin.multivideo.data.project.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jofin.multivideo.data.project.model.ClipTrackEntity
import com.jofin.multivideo.data.project.model.ExportStatusEntity
import com.jofin.multivideo.data.project.model.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY updatedAt DESC")
    fun observeProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :projectId")
    fun observeProjectEntity(projectId: String): Flow<ProjectEntity?>

    @Query("SELECT * FROM clips WHERE projectId = :projectId ORDER BY slotIndex ASC")
    fun observeClips(projectId: String): Flow<List<ClipTrackEntity>>

    @Query("SELECT * FROM export_status WHERE projectId = :projectId")
    fun observeExportStatus(projectId: String): Flow<ExportStatusEntity?>

    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getProjectEntity(projectId: String): ProjectEntity?

    @Query("SELECT * FROM clips WHERE projectId = :projectId ORDER BY slotIndex ASC")
    suspend fun getClips(projectId: String): List<ClipTrackEntity>

    @Query("SELECT COUNT(*) FROM clips WHERE projectId = :projectId")
    suspend fun countClips(projectId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProject(project: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertClips(clips: List<ClipTrackEntity>)

    @Query("DELETE FROM clips WHERE projectId = :projectId")
    suspend fun deleteClipsForProject(projectId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExportStatus(status: ExportStatusEntity)

    @Transaction
    suspend fun replaceProject(project: ProjectEntity, clips: List<ClipTrackEntity>) {
        upsertProject(project)
        deleteClipsForProject(project.id)
        upsertClips(clips)
    }
}
