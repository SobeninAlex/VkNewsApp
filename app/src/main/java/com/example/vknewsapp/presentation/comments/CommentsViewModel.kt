package com.example.vknewsapp.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.vknewsapp.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentScreenState.Comment(
                feedPost = feedPost,
                comments = it
            ) as CommentScreenState
        }
        .onStart { emit(CommentScreenState.Loading) }

}