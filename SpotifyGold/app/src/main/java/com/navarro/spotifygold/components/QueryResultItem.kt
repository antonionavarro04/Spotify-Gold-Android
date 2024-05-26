package com.navarro.spotifygold.components

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.entities.DtoResultEntity
import com.navarro.spotifygold.utils.formatLikes
import com.navarro.spotifygold.utils.formatNumber
import com.navarro.spotifygold.utils.formatTime
import com.navarro.spotifygold.utils.formatViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@Composable
fun IconInfo(
    text: String,
    icon: ImageVector
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Like icon
        Icon(
            imageVector = icon,
            contentDescription = "Like Icon",
            modifier = Modifier
                .size(20.dp)
        )

        // Text for likes
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun QueryResultItem(
    audioInfo: DtoResultEntity, onDownloadClick: (DtoResultEntity) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable {
                onDownloadClick(audioInfo)
            }) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(audioInfo.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight()
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (audioInfo.title.lowercase().contains("sigma")) {
                    "JA🍷🗿・${audioInfo.title}"
                } else {
                    audioInfo.title
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Row | Arrange Space between
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${formatTime(audioInfo.duration)}・${audioInfo.authorName}",
                    color = Color.LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
        Column (
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconInfo(
                text = formatViews(audioInfo.views),
                icon = Icons.Default.RemoveRedEye
            )
            IconInfo(
                text = formatLikes(audioInfo.likes),
                icon = Icons.Default.ThumbUp
            )
        }
    }
}
