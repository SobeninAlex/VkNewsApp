package com.example.vknewsapp.ui

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
import com.example.vknewsapp.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = mainViewModel.feedPosts.observeAsState(listOf())

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
            items = feedPosts.value,
            key = { it.id }
        ) { feedPost ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { swipeToDismiss ->
                    if (swipeToDismiss == SwipeToDismissBoxValue.EndToStart) {
                        mainViewModel.deleteFeedPost(feedPost)
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
                    onLikeClickListener = { statisticItem ->
                        mainViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    },
                    onShareClickListener = { statisticItem ->
                        mainViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    },
                    onViewClickListener = { statisticItem ->
                        mainViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    },
                    onCommentClickListener = { statisticItem ->
                        mainViewModel.updateCount(feedPost = feedPost, item = statisticItem)
                    }
                )
            }
        }
    }
}