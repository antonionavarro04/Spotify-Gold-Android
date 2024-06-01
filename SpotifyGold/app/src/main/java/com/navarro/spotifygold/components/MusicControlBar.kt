package com.navarro.spotifygold.components

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.ui.theme.*

@Composable
fun MusicControlBar(
    mediaPlayer: MediaPlayer,
    queue: MutableList<AudioDRO>,
    current: MutableState<AudioDRO>
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
        val currentSong = current.value

        Row( // Invisible container
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .background(Black30Transparent)
        ) {
            Column {
                Row( // Real Bar
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Black80)
                        .padding(10.dp, 0.dp)
                        .pointerInput(Unit) {
                            var totalDragAmount = 0f
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    totalDragAmount += dragAmount
                                },
                                onDragEnd = {
                                    if (totalDragAmount < -100) { // Swipe left
                                        try {
                                            val currentIndex = queue.indexOf(current.value)
                                            current.value = queue[currentIndex + 1]
                                        } catch (e: IndexOutOfBoundsException) {
                                            current.value = queue[0]
                                        }
                                    } else if (totalDragAmount > 100) { // Swipe right
                                        try {
                                            val currentIndex = queue.indexOf(current.value)
                                            current.value = queue[currentIndex - 1]
                                        } catch (e: IndexOutOfBoundsException) {
                                            current.value = queue[queue.size - 1]
                                        }
                                    }

                                    current.value.let { currentAudio ->
                                        mediaPlayer.reset()
                                        mediaPlayer.setDataSource(currentAudio.route)
                                        mediaPlayer.prepare()
                                        mediaPlayer.start()
                                    }
                                    totalDragAmount = 0f
                                }
                            )
                        }

                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        Text(
                            text = currentSong.metadata?.title
                                ?: currentSong.route
                                    .split("/")
                                    .last(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp
                        )
                        Text(
                            text = currentSong.metadata?.author?.name
                                ?: currentSong.route,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = Gold50
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp)
                    ) {
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
}
