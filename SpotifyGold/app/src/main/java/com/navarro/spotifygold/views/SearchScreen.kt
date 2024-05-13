package com.navarro.spotifygold.views

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.navarro.spotifygold.R
import com.navarro.spotifygold.components.QueryResultItem
import com.navarro.spotifygold.components.SearchBar
import com.navarro.spotifygold.entities.DtoResultEntity

@Composable
fun SearchScreen() {

    val query = remember { mutableStateOf("") }
    val focusState = remember { mutableStateOf(false) }
    val audioList = mutableStateListOf<DtoResultEntity>()

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
                model = "https://spotifygold.azurewebsites.net/favicon.ico",
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
            SearchBar(
                query = query,
                list = audioList
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(audioList.size) { index ->
                val audioInfo = audioList[index]
                QueryResultItem(audioInfo = audioInfo)
            }
        }
    }
}
