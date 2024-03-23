package com.example.vknewsapp.presentation.comments

import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.PostComment

sealed class CommentScreenState {

    data object Initial : CommentScreenState()

    data object Loading : CommentScreenState()

    data class Comment(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentScreenState()

}