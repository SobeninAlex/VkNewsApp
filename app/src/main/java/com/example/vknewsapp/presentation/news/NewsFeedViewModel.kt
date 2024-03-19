package com.example.vknewsapp.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vknewsapp.data.repository.NewsFeedRepository
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> get() = _screenState

    private val _isLoadingContent = MutableStateFlow(true)
    val isLoadingContent = _isLoadingContent.asStateFlow()

    private val repository = NewsFeedRepository(application)

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = repository.loadRecommendations()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
            _isLoadingContent.value = false
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
        }
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifiedPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistic

        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }

        val newFeedPost = feedPost.copy(
            statistic = newStatistics
        )

        val newPosts = modifiedPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun deleteFeedPost(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val posts = currentState.posts.toMutableList()
        posts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = posts)
    }

}