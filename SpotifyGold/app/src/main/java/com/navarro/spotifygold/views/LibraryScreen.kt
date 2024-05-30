package com.navarro.spotifygold.views

import android.media.MediaPlayer
import android.os.Environment
import android.os.MessageQueue
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.navarro.spotifygold.services.readMusicFolder
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Gold50
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileNotFoundException

@Composable
@OptIn(DelicateCoroutinesApi::class)
fun LibraryScreen(
    mediaPlayer: MediaPlayer,
    queue: MutableList<AudioDRO>
) {
    val context = LocalContext.current
    var files: MutableList<AudioDRO> = remember { mutableListOf() }
    GlobalScope.launch {
        files = readMusicFolder(context)
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
                    text = "Library",
                    color = Black0,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                InteractableIconButton(
                    icon = Icons.Filled.SearchOff,
                    onClick = {
                        StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
                    }
                )
                InteractableIconButton(
                    icon = Icons.Filled.Add,
                    onClick = {
                        StaticToast.showToast(context.getString(R.string.error_not_implemented_yet))
                    }
                )
            }
        }
        LazyColumn(
            content = {
                items(files.size) { index ->

                    val audioDRO = files[index]

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
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
                                queue.add(audioDRO)
                            }
                    ) {
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
                                    text = if (audioDRO.metadata != null) {
                                        audioDRO.metadata!!.title
                                    } else {
                                        audioDRO.route
                                    },
                                    color = Black0,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(20.dp, 0.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (audioDRO.metadata != null) {
                                        audioDRO.metadata!!.author.name
                                    } else {
                                        audioDRO.route
                                    },
                                    color = Gold50,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(20.dp, 0.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        InteractableIconButton(
                            icon = Icons.Filled.MoreVert,
                            onClick = {
                                StaticToast.showToast(
                                    context.getString(R.string.error_not_implemented_yet)
                                )
                            }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }
        )
    }
}
