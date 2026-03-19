package com.jofin.multivideo.core.playback

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.exoplayer.ExoPlayer
import com.jofin.multivideo.core.timeline.TimelineMath
import com.jofin.multivideo.domain.model.Project
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreviewSessionManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val sessionScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val players = linkedMapOf<String, ExoPlayer>()
    private var driftJob: Job? = null
    private var project: Project? = null

    fun attachProject(project: Project) {
        this.project = project
        val known = players.keys.toSet()
        project.clips.forEach { clip ->
            if (clip.id !in known) {
                players[clip.id] = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri(Uri.parse(clip.uri)))
                    prepare()
                    volume = 0f
                    playWhenReady = false
                }
            }
        }
        players.keys.filterNot { id -> project.clips.any { it.id == id } }.forEach { removePlayer(it) }
        startDriftCorrection()
    }

    fun players(): Map<String, ExoPlayer> = players

    fun play(selectedAudioClipId: String?) {
        updateAudibleTrack(selectedAudioClipId)
        players.values.forEach { it.playWhenReady = true }
    }

    fun pause() {
        players.values.forEach { it.pause() }
    }

    fun seekTo(projectTimeMs: Long, selectedAudioClipId: String?) {
        val currentProject = project ?: return
        currentProject.clips.forEach { clip ->
            val sourcePosition = TimelineMath.sourcePositionForProjectTime(clip, projectTimeMs)
            val player = players[clip.id] ?: return@forEach
            if (sourcePosition == null) {
                player.pause()
                player.seekTo(clip.trimStartMs.coerceAtLeast(0L))
                player.volume = 0f
            } else {
                player.seekTo(sourcePosition)
                player.volume = if (clip.id == selectedAudioClipId && !clip.muted && clip.hasAudio) {
                    clip.volume.coerceIn(0f, 1f)
                } else {
                    0f
                }
            }
        }
    }

    fun releaseAll() {
        driftJob?.cancel()
        players.keys.toList().forEach(::removePlayer)
    }

    private fun updateAudibleTrack(selectedAudioClipId: String?) {
        val currentProject = project ?: return
        currentProject.clips.forEach { clip ->
            players[clip.id]?.volume = if (clip.id == selectedAudioClipId && !clip.muted && clip.hasAudio) {
                clip.volume.coerceIn(0f, 1f)
            } else {
                0f
            }
        }
    }

    private fun startDriftCorrection() {
        driftJob?.cancel()
        driftJob = sessionScope.launch {
            while (isActive) {
                val currentProject = project
                if (currentProject != null && players.isNotEmpty()) {
                    val master = players[currentProject.clips.firstOrNull()?.id] ?: players.values.first()
                    val projectTime = master.currentPosition
                    currentProject.clips.forEach { clip ->
                        val expected = TimelineMath.sourcePositionForProjectTime(clip, projectTime) ?: return@forEach
                        val player = players[clip.id] ?: return@forEach
                        val drift = kotlin.math.abs(player.currentPosition - expected)
                        when {
                            drift > 300L -> player.seekTo(expected)
                            drift in 60L..300L -> player.playbackParameters = PlaybackParameters(1.03f)
                            else -> player.playbackParameters = PlaybackParameters(1f)
                        }
                    }
                }
                delay(250L)
            }
        }
    }

    private fun removePlayer(id: String) {
        players.remove(id)?.release()
    }
}
