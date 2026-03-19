package com.jofin.multivideo.domain.repository

import com.jofin.multivideo.domain.model.ExportStatus
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.model.ProjectSummary
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun observeRecentProjects(): Flow<List<ProjectSummary>>
    fun observeProject(projectId: String): Flow<Project?>
    fun observeExportStatus(projectId: String): Flow<ExportStatus?>
    suspend fun createProject(project: Project)
    suspend fun saveProject(project: Project)
    suspend fun loadProject(projectId: String): Project?
    suspend fun updateExportStatus(status: ExportStatus)
}
