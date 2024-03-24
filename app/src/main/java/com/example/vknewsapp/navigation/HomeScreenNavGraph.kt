package com.example.vknewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknewsapp.domain.entity.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }

        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                //примитивные аргументы
//                navArgument(Screen.KEY_FEED_POST_ID) {
//                    type = NavType.IntType
//                },
//                navArgument(Screen.KEY_FEED_POST_CONTENT_TEXT) {
//                    type = NavType.StringType
//                }

                //аргументы - объекты
//                navArgument(Screen.KEY_FEED_POST) {
//                    type = NavType.StringType
//                }

                //аргументы - объекты через кастомный тип
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPost.NavigationType
                }
            )
        ) {
            //примитивные аргументы
//            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
//            val feedPostContentText =
//                it.arguments?.getString(Screen.KEY_FEED_POST_CONTENT_TEXT) ?: ""

            //аргументы - объекты
//            val feedPostJson = it.arguments?.getString(Screen.KEY_FEED_POST) ?: ""
//            val feedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)

            //аргументы - объекты через кастомный тип
            val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST) ?: throw RuntimeException("Args is null")

            commentScreenContent(feedPost
//                FeedPost(
//                    id = feedPostId,
//                    contentText = feedPostContentText
//                )
            )
        }
    }
}