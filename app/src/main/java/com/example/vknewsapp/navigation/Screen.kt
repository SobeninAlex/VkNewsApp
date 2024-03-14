package com.example.vknewsapp.navigation

sealed class Screen(
    val route: String
) {

    data object Home : Screen(ROUTE_HOME)
    data object Favourite : Screen(ROUTE_FAVOURITE)
    data object Profile : Screen(ROUTE_PROFILE)

    private companion object {
        const val ROUTE_HOME = "route_home"
        const val ROUTE_FAVOURITE = "route_favourite"
        const val ROUTE_PROFILE = "route_profile"
    }

}