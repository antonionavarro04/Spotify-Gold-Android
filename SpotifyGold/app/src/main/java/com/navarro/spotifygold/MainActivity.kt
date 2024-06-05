package com.navarro.spotifygold

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.navarro.spotifygold.components.BottomNavigationBar
import com.navarro.spotifygold.components.MusicControlBar
import com.navarro.spotifygold.entities.AudioDRO
import com.navarro.spotifygold.navigation.Navigation
import com.navarro.spotifygold.navigation.SpotifyNavigation
import com.navarro.spotifygold.services.MediaPlayerSingleton.mediaPlayer
import com.navarro.spotifygold.services.StaticToast
import com.navarro.spotifygold.ui.theme.SpotifyGoldTheme
import com.navarro.spotifygold.ui.theme.Transparent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            StaticToast.toast = Toast.makeText(context, "Undefined Toast", Toast.LENGTH_SHORT)

            SpotifyGoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Black
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val queue = remember { mutableStateListOf<AudioDRO>() }
    val current = remember { mutableStateOf(
        AudioDRO(
            metadata = null,
            route = "???",
            pos = 0
        )
    ) }
    val currentDestination = remember { mutableStateOf("") }

    mediaPlayer = MediaPlayer.create(context, R.raw.the_dark_of_the_matinee)

    LaunchedEffect(navController.currentBackStackEntryAsState()) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestination.value = destination.route ?: ""
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (currentDestination.value != Navigation.CURRENT.name) {
                Column(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                ) {
                    MusicControlBar(
                        navController = navController,
                        queue = queue,
                        current = current,
                    )
                    BottomNavigationBar(
                        navController = navController
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Transparent)
                    .align(Alignment.Center) // Align the inner Box to the center of the parent Box
            )
            SpotifyNavigation(
                navController = navController,
                queue = queue,
                current = current
            )
        }
    }
}