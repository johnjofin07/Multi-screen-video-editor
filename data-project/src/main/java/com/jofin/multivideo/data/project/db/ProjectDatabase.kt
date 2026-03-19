package com.jofin.multivideo.data.project.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jofin.multivideo.data.project.model.ClipTrackEntity
import com.jofin.multivideo.data.project.model.ExportStatusEntity
import com.jofin.multivideo.data.project.model.ProjectEntity

@Database(
    entities = [ProjectEntity::class, ClipTrackEntity::class, ExportStatusEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(ProjectTypeConverters::class)
abstract class ProjectDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}
