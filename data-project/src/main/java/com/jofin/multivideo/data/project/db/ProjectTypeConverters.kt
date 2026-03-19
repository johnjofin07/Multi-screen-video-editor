package com.jofin.multivideo.data.project.db

import androidx.room.TypeConverter
import com.jofin.multivideo.domain.model.AudioMode
import com.jofin.multivideo.domain.model.LayoutType

class ProjectTypeConverters {
    @TypeConverter
    fun fromLayoutType(value: LayoutType): String = value.name

    @TypeConverter
    fun toLayoutType(value: String): LayoutType = LayoutType.valueOf(value)

    @TypeConverter
    fun fromAudioMode(value: AudioMode): String = value.name

    @TypeConverter
    fun toAudioMode(value: String): AudioMode = AudioMode.valueOf(value)
}
