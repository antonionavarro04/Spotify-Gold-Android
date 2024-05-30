package com.navarro.spotifygold.components

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.ui.theme.*

@Composable
fun MusicControlBar(
    mediaPlayer: MediaPlayer,
    queue: MutableList<AudioDRO>
) {
    var isPlaying by remember { mutableStateOf(mediaPlayer.isPlaying) }

    LaunchedEffect(mediaPlayer) {
        val listener = MediaPlayer.OnCompletionListener {
            isPlaying = mediaPlayer.isPlaying
        }
        mediaPlayer.setOnCompletionListener(listener)
        mediaPlayer.setOnPreparedListener {
            isPlaying = mediaPlayer.isPlaying
        }

        mediaPlayer.setOnInfoListener { _, what, _ ->
            if (
                what == MediaPlayer.MEDIA_INFO_BUFFERING_START ||
                what == MediaPlayer.MEDIA_INFO_BUFFERING_END
            ) {
                isPlaying = mediaPlayer.isPlaying
                true
            } else {
                false
            }
        }
    }

    if (queue.isNotEmpty()) {
        val currentSong = queue[0]

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(Black10Transparent)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .background(Black90)
            ) {
                Row {
                    Column {
                        Text(text = currentSong.metadata!!.title)
                        Text(text = currentSong.metadata!!.author.name)
                    }
                }
                Row {
                    IconButton(onClick = {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                        } else {
                            mediaPlayer.start()
                        }
                        // Update the state to reflect the current playback status
                        isPlaying = mediaPlayer.isPlaying
                    }) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            imageVector = if (isPlaying) {
                                Icons.Filled.Pause
                            } else {
                                Icons.Filled.PlayArrow
                            },
                            contentDescription = "Play/Pause"
                        )
                    }
                }
            }
        }
    }
}
