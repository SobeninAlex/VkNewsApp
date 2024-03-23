package com.example.vknewsapp.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsapp.data.repository.NewsFeedRepository
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.PostComment
import com.example.vknewsapp.extensions.mergeWith
import com.example.vknewsapp.presentation.news.NewsFeedScreenState
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val repository = NewsFeedRepository(application)

    val screenState = repository.loadComments(feedPost)
        .map {
            CommentScreenState.Comment(
                feedPost = feedPost,
                comments = it
            ) as CommentScreenState
        }
        .onStart { emit(CommentScreenState.Loading) }
}