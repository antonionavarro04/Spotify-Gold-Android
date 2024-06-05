package com.navarro.spotifygold.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.navarro.spotifygold.R
import com.navarro.spotifygold.components.global.InteractableIconButton
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.services.MediaPlayerSingleton.mediaPlayer
import com.navarro.spotifygold.services.MediaPlayerSingleton.next
import com.navarro.spotifygold.services.MediaPlayerSingleton.pause
import com.navarro.spotifygold.services.MediaPlayerSingleton.play
import com.navarro.spotifygold.services.MediaPlayerSingleton.playPause
import com.navarro.spotifygold.services.MediaPlayerSingleton.previous
import com.navarro.spotifygold.services.StaticToast
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Black80
import com.navarro.spotifygold.ui.theme.Gold50
import com.navarro.spotifygold.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentScreen(
    navController: NavController,
    queue: MutableList<AudioDRO>,
    current: MutableState<AudioDRO>
) {
    val context = LocalContext.current

    val updateScope = rememberCoroutineScope()

    val fontSize = 12.sp

    val from = remember { mutableStateOf("") }

    val playedTime = remember { mutableStateOf(0) }

    val isPlaying = remember { mutableStateOf(false) }

    val getFrom: (route: String) -> String = {
        val route = it.split("/")
        when (route.size) {
            6 -> "${route[4]}/${route[5]}"
            else -> "???"
        }
    }

    LaunchedEffect(mediaPlayer.isPlaying) {
        isPlaying.value = mediaPlayer.isPlaying
    }

    LaunchedEffect(current.value.route) {
        try {
            from.value = getFrom(current.value.route)
        } catch (e: Exception) {
            from.value = "???"
        }
    }

    LaunchedEffect(Unit) {
        updateScope.launch {
            while (isActive) {
                playedTime.value = mediaPlayer.currentPosition
                delay(100)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row( // Route
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            InteractableIconButton(
                icon = Icons.Filled.KeyboardArrowDown
            ) {
                navController.popBackStack()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.playing_from).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
                Text(
                    text = from.value,
                    fontSize = fontSize
                )
            }
            InteractableIconButton(
                icon = Icons.Filled.MoreVert
            ) {
                StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(25.dp)
                .clip(RoundedCornerShape(10.dp))
        ) { // Image
            Image(
                painter = rememberAsyncImagePainter(
                    current.value.metadata?.thumbnail
                        ?: Constants.defaultImage
                ),
                contentDescription = "Song Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) { // Title, Author && Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = current.value.getSafeTitle(),
                        fontSize = 14.sp,
                        color = Black0,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = current.value.getSafeArtist(),
                        fontSize = 12.sp,
                        color = Gold50,
                    )
                }
                InteractableIconButton(
                    icon = Icons.Outlined.AddCircleOutline,
                    tint = Black0,
                    size = 32.dp
                ) {
                    StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
                }
            }
            Slider(
                value = playedTime.value.toFloat(),
                onValueChange = {
                    pause()
                    val longPos = it.toInt();
                    mediaPlayer.seekTo(longPos)
                },
                onValueChangeFinished = {
                    play()
                },
                valueRange = 0f..mediaPlayer.duration.toFloat(),
                thumb = { },
                colors = SliderDefaults.colors(
                    activeTrackColor = Gold50,
                    inactiveTrackColor = Black80
                ),
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) { // Play/Pause, Next/Previous, Shuffle/Repeat
                val generalSize = 50.dp
                InteractableIconButton(
                    icon = Icons.Filled.SkipPrevious,
                    size = generalSize
                ) {
                    previous(queue, current)
                }
                IconButton(
                    onClick = { playPause() },
                    modifier = Modifier
                        .size(70.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(0.dp)
                            .size(70.dp),
                        imageVector = if (isPlaying.value) Icons.Filled.PauseCircle
                        else Icons.Filled.PlayCircle,
                        contentDescription = "Play/Pause",
                        tint = Black0
                    )
                }
                InteractableIconButton(
                    icon = Icons.Filled.SkipNext,
                    size = generalSize
                ) {
                    next(queue, current)
                }
            }
        }
    }
}
