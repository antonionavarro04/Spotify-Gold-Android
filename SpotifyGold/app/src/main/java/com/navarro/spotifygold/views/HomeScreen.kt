package com.navarro.spotifygold.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.navarro.spotifygold.entities.ArtistDRO
import com.navarro.spotifygold.entities.metadata.AuthorEntity
import com.navarro.spotifygold.services.readArtists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val x = remember { mutableStateListOf<ArtistDRO>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val y = readArtists(context)

            x.addAll(y)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Lazy column of x
        LazyColumn(
            content = {
                items(x.size) { index ->
                    Text(text = x[index].author.id)
                    Text(text = x[index].author.name)
                    Text(text = x[index].author.url)
                    Text(text = x[index].songsCount.toString())
                }
            }
        )
    }
}
