package com.jofin.multivideo.data.project

import android.content.Context
import androidx.room.Room
import com.jofin.multivideo.data.project.db.ProjectDao
import com.jofin.multivideo.data.project.db.ProjectDatabase
import com.jofin.multivideo.domain.repository.ProjectRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectDataBindings {
    @Binds
    @Singleton
    abstract fun bindProjectRepository(impl: RoomProjectRepository): ProjectRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProjectDataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProjectDatabase =
        Room.databaseBuilder(context, ProjectDatabase::class.java, "multivideo.db").build()

    @Provides
    fun provideProjectDao(database: ProjectDatabase): ProjectDao = database.projectDao()
}
