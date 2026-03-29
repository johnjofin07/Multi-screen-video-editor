package com.jofin.multivideo.data.project.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jofin.multivideo.data.project.model.ClipTrackEntity
import com.jofin.multivideo.data.project.model.ExportStatusEntity
import com.jofin.multivideo.data.project.model.ProjectEntity

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE clips ADD COLUMN segmentsJson TEXT NOT NULL DEFAULT '[]'")
        db.execSQL("ALTER TABLE clips ADD COLUMN audioFadeInMs INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE clips ADD COLUMN audioFadeOutMs INTEGER NOT NULL DEFAULT 0")
    }
}

@Database(
    entities = [ProjectEntity::class, ClipTrackEntity::class, ExportStatusEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(ProjectTypeConverters::class)
abstract class ProjectDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}
