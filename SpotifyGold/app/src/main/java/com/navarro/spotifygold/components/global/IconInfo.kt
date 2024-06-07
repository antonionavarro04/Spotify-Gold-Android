package com.navarro.spotifygold.components.global

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconInfo(
    text: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
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