package com.navarro.spotifygold.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.navarro.spotifygold.views.HomeScreen
import com.navarro.spotifygold.views.LibraryScreen
import com.navarro.spotifygold.views.SearchScreen

@Composable
fun SpotifyNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Navigation.SEARCH.name
    ) {
        composable(Navigation.HOME.name) {
            HomeScreen()
        }
        composable(Navigation.SEARCH.name) {
            SearchScreen()
        }
        composable(Navigation.LIBRARY.name) {
            LibraryScreen()
        }
    }
}