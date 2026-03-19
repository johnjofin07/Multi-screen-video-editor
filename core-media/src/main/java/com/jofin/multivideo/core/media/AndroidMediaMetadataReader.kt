package com.jofin.multivideo.core.media

import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.OpenableColumns
import com.jofin.multivideo.domain.model.VideoMetadata
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface MediaMetadataReader {
    suspend fun read(uri: Uri): Result<VideoMetadata>
}

@Singleton
class AndroidMediaMetadataReader @Inject constructor(
    @ApplicationContext private val context: Context,
) : MediaMetadataReader {
    override suspend fun read(uri: Uri): Result<VideoMetadata> = withContext(Dispatchers.IO) {
        runCatching {
            val resolver = context.contentResolver
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)
            val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
            val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt() ?: 0
            val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt() ?: 0
            val rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)?.toInt() ?: 0
            val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
                ?: resolver.getType(uri).orEmpty()
            val hasAudio = (retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO) ?: "yes") == "yes"
            retriever.release()
            VideoMetadata(
                uri = uri.toString(),
                displayName = resolver.queryDisplayName(uri) ?: uri.lastPathSegment.orEmpty(),
                durationMs = durationMs,
                width = width,
                height = height,
                rotationDegrees = rotation,
                mimeType = mimeType,
                hasAudio = hasAudio,
            )
        }
    }
}

private fun ContentResolver.queryDisplayName(uri: Uri): String? =
    query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) cursor.getString(0) else null
    }

interface UriPermissionManager {
    fun takeReadPermission(uri: Uri)
}

@Singleton
class AndroidUriPermissionManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : UriPermissionManager {
    override fun takeReadPermission(uri: Uri) {
        runCatching {
            context.contentResolver.takePersistableUriPermission(
                uri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        }
    }
}
