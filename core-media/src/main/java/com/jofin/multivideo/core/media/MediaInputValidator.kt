package com.jofin.multivideo.core.media

import com.jofin.multivideo.domain.model.VideoMetadata

object MediaInputValidator {
    fun validateSelectionCount(count: Int): String? = when {
        count < 2 -> "Pick at least 2 videos."
        count > 4 -> "Pick no more than 4 videos."
        else -> null
    }

    fun validateMetadata(metadata: VideoMetadata): String? = when {
        metadata.durationMs <= 0L -> "${metadata.displayName} has zero duration."
        metadata.mimeType.isBlank() -> "${metadata.displayName} is unreadable."
        else -> null
    }
}
