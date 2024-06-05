package com.navarro.spotifygold.services

import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.services.notification.createNotificationV1

object MediaPlayerSingleton {
    var mediaPlayer: MediaPlayer = MediaPlayer()

    fun playPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    fun play() {
        mediaPlayer.start()
    }

    fun play(uri: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun next(queue: MutableList<AudioDRO>, current: MutableState<AudioDRO>) {
        try {
            val currentIndex = queue.indexOf(current.value)
            current.value = queue[currentIndex + 1]
        } catch (e: IndexOutOfBoundsException) {
            current.value = queue[0]
        }

        play(current.value.route)
    }

    fun previous(queue: MutableList<AudioDRO>, current: MutableState<AudioDRO>) {
        try {
            val currentIndex = queue.indexOf(current.value)
            current.value = queue[currentIndex - 1]
        } catch (e: IndexOutOfBoundsException) {
            current.value = queue[queue.size - 1]
        }

        play(current.value.route)
    }
}