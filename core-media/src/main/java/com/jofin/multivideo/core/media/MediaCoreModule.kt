package com.jofin.multivideo.core.media

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaCoreModule {
    @Binds
    @Singleton
    abstract fun bindMetadataReader(impl: AndroidMediaMetadataReader): MediaMetadataReader

    @Binds
    @Singleton
    abstract fun bindUriPermissionManager(impl: AndroidUriPermissionManager): UriPermissionManager
}
