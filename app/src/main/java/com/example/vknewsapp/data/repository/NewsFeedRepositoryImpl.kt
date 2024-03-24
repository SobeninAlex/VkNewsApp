package com.example.vknewsapp.data.repository

import android.app.Application
import android.util.Log
import com.example.vknewsapp.data.mapper.NewsFeedMapper
import com.example.vknewsapp.data.network.ApiFactory
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.StatisticItem
import com.example.vknewsapp.domain.entity.StatisticType
import com.example.vknewsapp.domain.entity.AuthState
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import com.example.vknewsapp.utils.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl(application: Application) : NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token
        get() = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendations(token = getAccessToken())
            } else {
                apiService.loadRecommendations(token = getAccessToken(), startFrom = startFrom)
            }

            nextFrom = response.newsFeedContent.nextFrom

            val posts = mapper.mapResponseToPosts(newsFeedResponse = response)
            _feedPosts.addAll(posts)

            emit(feedPosts)
        }
    }
        .retry(3) {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .catch {
            Log.d("NewsFeedRepository", "oops! loadedListFlow")
        }

    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            Log.d("NewsFeedRepository", "access_token: ${token?.accessToken}")

            val authState = if (loggedIn) {
                AuthState.Authorized
            } else {
                AuthState.NotAuthorized
            }
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override fun loadComments(feedPost: FeedPost) = flow {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        emit(mapper.napResponseToComments(response))
    }
        .retry(3) {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .catch {
            Log.d("NewsFeedRepository", "oops! loadComments")
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

        val newLikesCount = response.likesCount.count

        val newStatistics = feedPost.statistic.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = newLikesCount))
        }

        val newPost = feedPost.copy(
            statistic = newStatistics,
            isLiked = !feedPost.isLiked
        )

        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}