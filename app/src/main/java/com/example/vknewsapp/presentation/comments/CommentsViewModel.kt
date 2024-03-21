package com.example.vknewsapp.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsapp.data.repository.NewsFeedRepository
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.PostComment
import kotlinx.coroutines.launch

class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> get() = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = repository.loadComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = comments
            )
        }
    }

}