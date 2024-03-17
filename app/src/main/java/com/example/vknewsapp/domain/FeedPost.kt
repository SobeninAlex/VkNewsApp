package com.example.vknewsapp.domain

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavType
import com.example.vknewsapp.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

@Parcelize
data class FeedPost(
    val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val icon: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = LoremIpsum().toString(),
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistic: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 23)
    )
) : Parcelable {

    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }

    }

}