package com.jofin.multivideo.data.project.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.LayoutType

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
    val outputAspectRatio: String,
    val exportPresetLabel: String,
    val exportWidth: Int,
    val exportHeight: Int,
    val exportBitrate: Int,
    val exportFrameRate: Int,
    val exportAudioBitrate: Int,
    val layoutType: LayoutType,
    val audioMode: AudioMode,
    val backgroundColor: Long,
    val selectedAudioClipId: String?,
)

@Entity(
    tableName = "clips",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("projectId")],
)
data class ClipTrackEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val uri: String,
    val displayName: String,
    val durationMs: Long,
    val width: Int,
    val height: Int,
    val rotationDegrees: Int,
    val trimStartMs: Long,
    val trimEndMs: Long,
    val startOffsetMs: Long,
    val volume: Float,
    val muted: Boolean,
    val slotIndex: Int,
    val zIndex: Int,
    val mimeType: String,
    val hasAudio: Boolean,
    val segmentsJson: String = "[]",
    val audioFadeInMs: Long = 0L,
    val audioFadeOutMs: Long = 0L,
)

@Entity(tableName = "export_status")
data class ExportStatusEntity(
    @PrimaryKey val projectId: String,
    val state: String,
    val progress: Int,
    val outputUri: String?,
    val message: String?,
)
