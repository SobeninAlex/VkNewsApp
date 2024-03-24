package com.example.vknewsapp.presentation.comments

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment

sealed class CommentScreenState {

    data object Initial : CommentScreenState()

    data object Loading : CommentScreenState()

    data class Comment(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ) : CommentScreenState()

}