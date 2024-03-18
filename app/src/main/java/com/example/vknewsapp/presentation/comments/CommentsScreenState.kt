package com.example.vknewsapp.presentation.comments

import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.PostComment

sealed class CommentsScreenState {

    data object Initial : CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentsScreenState()

}