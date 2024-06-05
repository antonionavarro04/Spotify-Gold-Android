package com.navarro.spotifygold.components

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.services.MediaPlayerSingleton.mediaPlayer
import com.navarro.spotifygold.services.notification.createNotificationV1
import com.navarro.spotifygold.ui.theme.Black30Transparent
import com.navarro.spotifygold.ui.theme.Black80
import com.navarro.spotifygold.ui.theme.Gold50
import com.navarro.spotifygold.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun MusicControlBar(
    queue: MutableList<AudioDRO>,
    current: MutableState<AudioDRO>
) {
    val context = LocalContext.current

    val updateScope = rememberCoroutineScope() // Create a coroutine scope for the update

    var isPlaying by remember { mutableStateOf(mediaPlayer!!.isPlaying) }

    val playedTime = remember { mutableStateOf(0) }

    LaunchedEffect(mediaPlayer) {
        mediaPlayer.setOnCompletionListener {
            isPlaying = mediaPlayer.isPlaying

            try {
                val currentIndex = queue.indexOf(current.value)
                current.value = queue[currentIndex + 1]
            } catch (e: IndexOutOfBoundsException) {
                current.value = queue[0]
            }

            current.value.let { currentAudio ->
                mediaPlayer.reset()
                mediaPlayer.setDataSource(currentAudio.route)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        }
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

    LaunchedEffect(mediaPlayer.isPlaying) {
        isPlaying = mediaPlayer.isPlaying
    }

    LaunchedEffect(Unit) {
        updateScope.launch {
            while (isActive) {
                playedTime.value = mediaPlayer.currentPosition
                delay(10)  // Update every second
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
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Black80)
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
                Row(
                    // Real Bar
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp, 0.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            currentSong.metadata?.thumbnail ?: Constants.defaultImage
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .height(50.dp)
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
                    IconButton(onClick = {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                            createNotificationV1(context, mediaPlayer, currentSong)
                        } else {
                            mediaPlayer.start()
                            createNotificationV1(context, mediaPlayer, currentSong)
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
                Row( // Progress Bar
                    Modifier
                        .fillMaxWidth(
                            (playedTime.value.toFloat() / mediaPlayer.duration.toFloat())
                        )
                        .height(2.dp)
                        .background(Gold50)
                ) {

                }
            }
        }
    }
}

