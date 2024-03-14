package com.example.vknewsapp.domain

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.vknewsapp.R

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
)