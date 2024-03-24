package com.example.vknewsapp.domain.repository

import com.example.vknewsapp.domain.entity.AuthState
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    fun loadComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)

}