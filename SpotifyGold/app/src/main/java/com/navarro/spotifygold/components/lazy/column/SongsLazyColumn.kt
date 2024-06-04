package com.navarro.spotifygold.components.lazy.column

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.components.global.InteractableIconButton
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.services.getInfo
import com.navarro.spotifygold.ui.theme.Black0
import com.navarro.spotifygold.ui.theme.Black20
import com.navarro.spotifygold.ui.theme.Gold50
import java.io.FileNotFoundException

@Composable
fun SongsLazyColumn(
    mediaPlayer: MediaPlayer,
    files: List<AudioDRO>,
    queue: MutableList<AudioDRO>,
    current: MutableState<AudioDRO>
) {
    val context = LocalContext.current

    LazyColumn(content = {
        items(files.size) { index ->

            val audioDRO = files[index]

            val isPlaying = current.value.route == audioDRO.route

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

                        try {
                            getInfo(context, audioDRO.metadata!!.id)
                        } catch (e: NullPointerException) {
                            getInfo(
                                context,
                                audioDRO.route
                                    .split("/")
                                    .last()
                                    .split(".")[1]
                            )
                        } catch (e: Exception) {
                            StaticToast.showToast(
                                context.getString(R.string.error_file_not_downloaded)
                            )
                        }
                    }) {
                Image(
                    painter = rememberAsyncImagePainter(
                        audioDRO.metadata?.thumbnail
                            ?: "https://spotifygold.azurewebsites.net/favicon.ico"
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = audioDRO.metadata?.title ?: audioDRO.route.split("/").last(),
                        color = if (isPlaying) Gold50 else Black0,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(20.dp, 0.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = audioDRO.metadata?.author?.name ?: audioDRO.route,
                        color = Black20,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(20.dp, 0.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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