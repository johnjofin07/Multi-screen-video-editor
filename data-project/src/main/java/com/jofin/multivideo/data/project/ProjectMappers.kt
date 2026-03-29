package com.jofin.multivideo.data.project

import com.jofin.multivideo.data.project.model.ClipTrackEntity
import com.jofin.multivideo.data.project.model.ExportStatusEntity
import com.jofin.multivideo.data.project.model.ProjectEntity
import com.jofin.multivideo.domain.model.ClipTrack
import com.jofin.multivideo.domain.model.ExportPreset
import com.jofin.multivideo.domain.model.ExportState
import com.jofin.multivideo.domain.model.ExportStatus
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.model.ProjectSummary
import com.jofin.multivideo.domain.model.TrimSegment
import org.json.JSONArray
import org.json.JSONObject

internal fun Project.toEntity(): ProjectEntity = ProjectEntity(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    outputAspectRatio = outputAspectRatio,
    exportPresetLabel = exportPreset.label,
    exportWidth = exportPreset.width,
    exportHeight = exportPreset.height,
    exportBitrate = exportPreset.bitrate,
    exportFrameRate = exportPreset.frameRate,
    exportAudioBitrate = exportPreset.audioBitrate,
    layoutType = layoutType,
    audioMode = audioMode,
    backgroundColor = backgroundColor,
    selectedAudioClipId = selectedAudioClipId,
)

internal fun ClipTrack.toEntity(projectId: String): ClipTrackEntity = ClipTrackEntity(
    id = id,
    projectId = projectId,
    uri = uri,
    displayName = displayName,
    durationMs = durationMs,
    width = width,
    height = height,
    rotationDegrees = rotationDegrees,
    trimStartMs = trimStartMs,
    trimEndMs = trimEndMs,
    startOffsetMs = startOffsetMs,
    volume = volume,
    muted = muted,
    slotIndex = slotIndex,
    zIndex = zIndex,
    mimeType = mimeType,
    hasAudio = hasAudio,
    segmentsJson = segmentsToJson(segments),
    audioFadeInMs = audioFadeInMs,
    audioFadeOutMs = audioFadeOutMs,
)

internal fun ProjectEntity.toSummary(clipCount: Int): ProjectSummary = ProjectSummary(
    id = id,
    name = name,
    updatedAt = updatedAt,
    layoutType = layoutType,
    clipCount = clipCount,
)

internal fun ProjectEntity.toDomain(clips: List<ClipTrackEntity>): Project = Project(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    outputAspectRatio = outputAspectRatio,
    exportPreset = ExportPreset(
        label = exportPresetLabel,
        width = exportWidth,
        height = exportHeight,
        bitrate = exportBitrate,
        frameRate = exportFrameRate,
        audioBitrate = exportAudioBitrate,
    ),
    layoutType = layoutType,
    audioMode = audioMode,
    backgroundColor = backgroundColor,
    selectedAudioClipId = selectedAudioClipId,
    clips = clips.map { it.toDomain() },
)

internal fun ClipTrackEntity.toDomain(): ClipTrack = ClipTrack(
    id = id,
    uri = uri,
    displayName = displayName,
    durationMs = durationMs,
    width = width,
    height = height,
    rotationDegrees = rotationDegrees,
    trimStartMs = trimStartMs,
    trimEndMs = trimEndMs,
    startOffsetMs = startOffsetMs,
    volume = volume,
    muted = muted,
    slotIndex = slotIndex,
    zIndex = zIndex,
    mimeType = mimeType,
    hasAudio = hasAudio,
    segments = segmentsFromJson(segmentsJson),
    audioFadeInMs = audioFadeInMs,
    audioFadeOutMs = audioFadeOutMs,
)

private fun segmentsToJson(segments: List<TrimSegment>): String {
    val arr = JSONArray()
    segments.forEach { seg ->
        arr.put(JSONObject().apply {
            put("id", seg.id)
            put("startMs", seg.startMs)
            put("endMs", seg.endMs)
            put("selected", seg.selected)
        })
    }
    return arr.toString()
}

private fun segmentsFromJson(json: String): List<TrimSegment> {
    if (json.isBlank() || json == "[]") return emptyList()
    val arr = JSONArray(json)
    return (0 until arr.length()).map { i ->
        val obj = arr.getJSONObject(i)
        TrimSegment(
            id = obj.getString("id"),
            startMs = obj.getLong("startMs"),
            endMs = obj.getLong("endMs"),
            selected = obj.optBoolean("selected", false),
        )
    }
}

internal fun ExportStatus.toEntity(): ExportStatusEntity = ExportStatusEntity(
    projectId = projectId,
    state = state.name,
    progress = progress,
    outputUri = outputUri,
    message = message,
)

internal fun ExportStatusEntity.toDomain(): ExportStatus = ExportStatus(
    projectId = projectId,
    state = ExportState.valueOf(state),
    progress = progress,
    outputUri = outputUri,
    message = message,
)
