package com.navarro.spotifygold.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.SavedSearch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.navarro.spotifygold.navigation.Navigation

sealed class ItemsBottomNav (
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val title: String,
    val route: String
) {
    object Home : ItemsBottomNav(
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Home",
        Navigation.HOME.name
    )

    object Search : ItemsBottomNav(
        Icons.Filled.SavedSearch,
        Icons.Outlined.Search,
        "Search",
        Navigation.SEARCH.name
    )

    object Library : ItemsBottomNav(
        Icons.Filled.LibraryMusic,
        Icons.Outlined.LibraryMusic,
        "Library",
        Navigation.LIBRARY.name
    )
}