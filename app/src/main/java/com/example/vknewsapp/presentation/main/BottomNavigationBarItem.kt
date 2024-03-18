package com.example.vknewsapp.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vknewsapp.R
import com.example.vknewsapp.navigation.Screen

sealed class BottomNavigationBarItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    data object Home: BottomNavigationBarItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Outlined.Home
    )

    data object Favourite: BottomNavigationBarItem(
        screen = Screen.Favourite,
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.FavoriteBorder
    )

    data object Profile: BottomNavigationBarItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )

}