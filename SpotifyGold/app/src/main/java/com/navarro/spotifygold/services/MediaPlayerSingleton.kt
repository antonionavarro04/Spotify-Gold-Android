package com.navarro.spotifygold.services

import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import com.navarro.spotifygold.entities.AudioDRO

private enum class MediaNavigationModes(val value: Int) {
    NEXT(1),
    PREVIOUS(-1)
}

object MediaPlayerSingleton {
    var mediaPlayer: MediaPlayer = MediaPlayer()

    /**
     * Play or pause the current media
     */
    fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    /**
     * Play the current media
     */
    fun play() {
        mediaPlayer.start()
    }

    /**
     * Play a new media
     * @param uri Media URI
     */
    fun play(uri: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    /**
     * Pause the current media
     */
    fun pause() {
        mediaPlayer.pause()
    }

    /**
     * Play the next media in the queue
     * @param queue List of media
     * @param current Current media
     */
    fun next(queue: MutableList<AudioDRO>, current: MutableState<AudioDRO>) {
        navigate(queue, current, MediaNavigationModes.NEXT.value)
    }

    /**
     * Play the previous media in the queue
     * @param queue List of media
     * @param current Current media
     */
    fun previous(queue: MutableList<AudioDRO>, current: MutableState<AudioDRO>) {
        navigate(queue, current, MediaNavigationModes.PREVIOUS.value)
    }

    /**
     * Navigate through the queue
     * @param queue List of media
     * @param current Current media
     * @param step Step to navigate
     */
    private fun navigate(queue: MutableList<AudioDRO>, current: MutableState<AudioDRO>, step: Int) {
        when {
            SettingsSingleton.loop.value -> { }
            SettingsSingleton.shuffle.value -> current.value = random(queue, current.value)
            else -> {
                val currentIndex = queue.indexOf(current.value)
                val newIndex = (currentIndex + step).let {
                    when {
                        it >= queue.size -> 0
                        it < 0 -> queue.size - 1
                        else -> it
                    }
                }
                current.value = queue[newIndex]
            }
        }
        play(current.value.route)
    }

    /**
     * Get a random media from the queue
     * @param queue List of media
     * @param current Current media
     * @return Random media
     */
    private fun random(queue: MutableList<AudioDRO>, current: AudioDRO): AudioDRO {
        var newValue = current
        while (newValue == current) {
            newValue = queue.random()
        }
        return newValue
    }
}