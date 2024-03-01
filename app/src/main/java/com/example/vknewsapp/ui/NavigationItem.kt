package com.example.vknewsapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsapp.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home: NavigationItem(
        titleResId = R.string.navigation_item_home,
        icon = Icons.Outlined.Home
    )

    data object Favourite: NavigationItem(
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.FavoriteBorder
    )

    data object Profile: NavigationItem (
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )

}