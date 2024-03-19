package com.example.vknewsapp.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsapp.domain.FeedPost

/**
 * В jetpack compose почти всегда используется UDF - архитектура
 * Unidirectional Data Flow - однонаправленный поток данных
 */

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {
    val newsFeedViewModel= viewModel<NewsFeedViewModel>()
    val screenState = newsFeedViewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                newsFeedViewModel = newsFeedViewModel,
                paddingValues = paddingValues,
                onCommentClickListener = onCommentClickListener
            )
        }
        is NewsFeedScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPost>,
    newsFeedViewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding =  PaddingValues(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { feedPost ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { swipeToDismiss ->
                    if (swipeToDismiss == SwipeToDismissBoxValue.EndToStart) {
                        newsFeedViewModel.deleteFeedPost(feedPost)
                    }
                    true
                },
                positionalThreshold = { threshold ->
                    threshold * 2
                }
            )

            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp),
                            text = "Delete Item",
                            color = Color.Red,
                            fontSize = 24.sp,
                        )
                    }
                },
                enableDismissFromStartToEnd = false,
            ) {
                PostCard(
                    feedPost = feedPost,
                    onLikeClickListener = { _ ->
                        newsFeedViewModel.changeLikeStatus(feedPost)
                    },
                    onShareClickListener = { statisticItem ->
                        newsFeedViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    },
                    onViewClickListener = { statisticItem ->
                        newsFeedViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    },
                    onCommentClickListener = {
                        onCommentClickListener(feedPost)
                    }
                )
            }
        }
    }
}