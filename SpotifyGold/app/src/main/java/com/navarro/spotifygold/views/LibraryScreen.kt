package com.navarro.spotifygold.views

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.components.InteractableIconButton
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.services.getInfo
import com.navarro.spotifygold.services.readMusicFolder
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Gold50
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

@Composable
fun LibraryScreen(
    mediaPlayer: MediaPlayer,
    queue: MutableList<AudioDRO>,
    current: MutableState<AudioDRO>
) {
    var pos = 0

    val context = LocalContext.current
    val files = remember { mutableStateListOf<AudioDRO>() }
    LaunchedEffect(Unit) {
        // Load music files on a background thread
        val musicFiles = withContext(Dispatchers.IO) {
            readMusicFolder(context)
        }
        files.clear()
        files.addAll(musicFiles)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://spotifygold.azurewebsites.net/favicon.ico",
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .fillMaxWidth(0.2f)
                        .clip(CircleShape)
                )
                Text(
                    text = stringResource(id = R.string.navigation_library),
                    color = Black0,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InteractableIconButton(icon = Icons.Filled.SearchOff, onClick = {
                    StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
                })
                InteractableIconButton(icon = Icons.Filled.Add, onClick = {
                    StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
                })
            }
        }
        LazyColumn(content = {
            items(files.size) { index ->

                val audioDRO = files[index]
                audioDRO.pos = pos
                pos++

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp)
                        .height(50.dp)
                        .clickable {
                            try {
                                mediaPlayer.reset()
                                mediaPlayer.setDataSource(audioDRO.route)
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            } catch (e: FileNotFoundException) {
                                StaticToast.showToast(
                                    context.getString(R.string.error_file_not_found)
                                )
                            } catch (e: Exception) {
                                StaticToast.showToast(
                                    context.getString(R.string.error_unknown)
                                )
                            }

                            queue.clear()
                            queue.addAll(files)
                            current.value = audioDRO

                            getInfo(context, audioDRO.metadata!!.id)
                        }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                audioDRO.metadata?.thumbnail
                                    ?: "https://spotifygold.azurewebsites.net/favicon.ico"
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(50.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            Text(
                                text = audioDRO.metadata?.title ?: audioDRO.route.split("/").last(),
                                color = Black0,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(20.dp, 0.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = audioDRO.metadata?.author?.name ?: audioDRO.route,
                                color = Gold50,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(20.dp, 0.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    InteractableIconButton(icon = Icons.Filled.MoreVert, onClick = {
                        StaticToast.showToast(
                            context.getString(R.string.error_not_implemented_yet)
                        )
                    })
                }
            }
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        })
    }
}
