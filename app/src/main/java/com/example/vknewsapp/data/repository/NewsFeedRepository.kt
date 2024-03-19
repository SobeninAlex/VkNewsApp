package com.example.vknewsapp.data.repository

import android.app.Application
import com.example.vknewsapp.data.mapper.NewsFeedMapper
import com.example.vknewsapp.data.network.ApiFactory
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import com.example.vknewsapp.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost> get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPost> {
        val response = apiService.loadRecommendations(token = getAccessToken())
        val posts =  mapper.mapResponseToPosts(newsFeedResponse = response)
        _feedPosts.addAll(posts)
        return posts
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

        val newLikesCount = response.likesCount.count

        val newStatistics = feedPost.statistic.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = newLikesCount))
        }

        val newPost = feedPost.copy(
            statistic = newStatistics,
            isLiked = !feedPost.isLiked
        )

        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }

}