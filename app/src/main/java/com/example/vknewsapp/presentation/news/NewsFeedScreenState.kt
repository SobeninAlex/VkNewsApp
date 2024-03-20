package com.example.vknewsapp.presentation.news

import com.example.vknewsapp.domain.FeedPost

sealed class NewsFeedScreenState {

    data object Initial : NewsFeedScreenState()

    data object Loading : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()

}