package com.example.vknewsapp.data.network

import com.example.vknewsapp.data.model.LikesCountResponseDto
import com.example.vknewsapp.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("likes.add?type=post&v=5.199")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ) : LikesCountResponseDto

    @GET("likes.delete?type=post&v=5.199")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ) : LikesCountResponseDto

}