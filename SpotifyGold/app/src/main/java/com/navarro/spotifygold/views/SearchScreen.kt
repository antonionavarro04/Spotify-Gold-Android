package com.navarro.spotifygold.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.navarro.spotifygold.R
import com.navarro.spotifygold.StaticToast
import com.navarro.spotifygold.components.QueryResultItem
import com.navarro.spotifygold.components.SearchBar
import com.navarro.spotifygold.entities.AudioEntity
import com.navarro.spotifygold.ui.theme.Black50
import com.navarro.spotifygold.ui.theme.Black60
import com.navarro.spotifygold.ui.theme.White100

fun NIY() {
    StaticToast.showToast("Not Implemented Yet...")
}

@Composable
fun SearchScreen() {

    val query = remember { mutableStateOf("") }
    val focusState = remember { mutableStateOf(false) }
    val audioList = remember { mutableStateOf(mutableListOf<AudioEntity>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row( // Small Icon and Title
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = R.drawable.ic_temp_1,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .fillMaxWidth(0.2f)
                    .clip(shape = CircleShape)
            )
            Text(
                text = stringResource(id = R.string.and_navigation_search),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row( // Search Bar
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            SearchBar(query = query)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(audioList.value.size) { index ->
                val audioInfo = audioList.value[index]
                QueryResultItem(audioInfo = audioInfo)
            }
        }
    }
}
