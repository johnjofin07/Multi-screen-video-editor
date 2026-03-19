package com.jofin.multivideo.data.project

import com.jofin.multivideo.data.project.db.ProjectDao
import com.jofin.multivideo.domain.model.ExportStatus
import com.jofin.multivideo.domain.model.Project
import com.jofin.multivideo.domain.model.ProjectSummary
import com.jofin.multivideo.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomProjectRepository @Inject constructor(
    private val projectDao: ProjectDao,
) : ProjectRepository {
    override fun observeRecentProjects(): Flow<List<ProjectSummary>> =
        projectDao.observeProjects().map { projects ->
            projects.map { entity ->
                val clipCount = projectDao.countClips(entity.id)
                entity.toSummary(clipCount)
            }
        }

    override fun observeProject(projectId: String): Flow<Project?> =
        combine(
            projectDao.observeProjectEntity(projectId),
            projectDao.observeClips(projectId),
        ) { project, clips ->
            project?.toDomain(clips)
        }

    override fun observeExportStatus(projectId: String): Flow<ExportStatus?> =
        projectDao.observeExportStatus(projectId).map { status -> status?.toDomain() }

    override suspend fun createProject(project: Project) {
        saveProject(project)
    }

    override suspend fun saveProject(project: Project) {
        projectDao.replaceProject(project.toEntity(), project.clips.map { it.toEntity(project.id) })
    }

    override suspend fun loadProject(projectId: String): Project? {
        val project = projectDao.getProjectEntity(projectId) ?: return null
        val clips = projectDao.getClips(projectId)
        return project.toDomain(clips)
    }

    override suspend fun updateExportStatus(status: ExportStatus) {
        projectDao.upsertExportStatus(status.toEntity())
    }
}
