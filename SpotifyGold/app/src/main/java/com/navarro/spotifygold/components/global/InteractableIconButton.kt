package com.navarro.spotifygold.components.global

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.ui.theme.Black0

@Composable
fun InteractableIconButton(
    icon: ImageVector,
    tint: Color = Black0,
    contentDescription: String = "Icon-$icon",
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(0.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize(0.7f),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}