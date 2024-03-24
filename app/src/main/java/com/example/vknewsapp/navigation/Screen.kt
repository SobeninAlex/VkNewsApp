package com.example.vknewsapp.navigation

import android.net.Uri
import com.example.vknewsapp.domain.entity.FeedPost
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {

    data object NewsFeed : Screen(ROUTE_NEWS_FEED)

    data object Favourite : Screen(ROUTE_FAVOURITE)

    data object Profile : Screen(ROUTE_PROFILE)

    data object Home : Screen(ROUTE_HOME)

    data object Comments : Screen(ROUTE_COMMENTS) {
        private const val ROUTE_FOR_ARGS = "route_comments"
        fun getRouteWithArgs(feedPost: FeedPost): String {
//            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText.encode()}"

            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}"
        }
    }

    companion object {
//        const val KEY_FEED_POST_ID = "feed_post_id"
//        const val KEY_FEED_POST_CONTENT_TEXT = "feed_post_content_text"
        const val KEY_FEED_POST = "feed_post"

        const val ROUTE_NEWS_FEED = "route_news_feed"
        const val ROUTE_FAVOURITE = "route_favourite"
        const val ROUTE_PROFILE = "route_profile"
//        const val ROUTE_COMMENTS = "route_comments/{$KEY_FEED_POST_ID}/{$KEY_FEED_POST_CONTENT_TEXT}"
        const val ROUTE_COMMENTS = "route_comments/{$KEY_FEED_POST}"
        const val ROUTE_HOME = "route_home"
    }

}

//для экранирования в контенте спец символов
fun String.encode(): String {
    return Uri.encode(this)
}