package com.example.vknewsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem

class MainViewModel(

) : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(sourceList)
    val feedPosts: LiveData<List<FeedPost>> get() = _feedPosts

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val modifiedPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
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

        _feedPosts.value = modifiedPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
    }

    fun deleteFeedPost(feedPost: FeedPost) {
        val posts = feedPosts.value?.toMutableList() ?: mutableListOf()
        posts.remove(feedPost)
        _feedPosts.value = posts
    }

}