package com.example.vknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vknewsapp.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeedScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentScreenContent: @Composable (FeedPost) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {

        homeScreenNavGraph(
            newsFeedScreenContent = newsFeedScreenContent,
            commentScreenContent = commentScreenContent
        )

        composable(route = Screen.Favourite.route) {
            favouriteScreenContent()
        }

        composable(route = Screen.Profile.route) {
            profileScreenContent()
        }

    }
}