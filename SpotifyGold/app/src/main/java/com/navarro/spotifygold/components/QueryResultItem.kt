package com.navarro.spotifygold.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.navarro.spotifygold.entities.AudioEntity
import com.navarro.spotifygold.entities.DtoResultEntity

@Composable
fun QueryResultItem(
    audioInfo: DtoResultEntity
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { }
    ) {
        Box (
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = audioInfo.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }
        Column (
            // Arrange items evenly
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = audioInfo.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Song・${audioInfo.authorName}",
                color = Color.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
