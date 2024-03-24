package com.example.vknewsapp.presentation.comments

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vknewsapp.R
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment
import com.example.vknewsapp.presentation.ViewModelFactory
import com.example.vknewsapp.ui.theme.DarkBlue

@Composable
fun CommentsScreen(
    viewModelFactory: ViewModelFactory,
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {
    val commentsViewModel = viewModel<CommentsViewModel>(
        factory = viewModelFactory
    )
    val screenState = commentsViewModel.screenState.collectAsState(CommentScreenState.Initial)

    when (val currentState = screenState.value) {
        is CommentScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = DarkBlue
                )
            }
        }

        is CommentScreenState.Comment -> {
            CommentsView(
                onBackPressed = onBackPressed,
                currentState = currentState
            )
        }

        is CommentScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentsView(
    onBackPressed: () -> Unit,
    currentState: CommentScreenState.Comment
) {
    Scaffold(
        topBar = {
            Card(
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.comments_text),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 66.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = currentState.comments,
                key = { it.id },
            ) { postComment ->
                CommentItem(postComment = postComment)
            }
        }
    }
}

@Composable
private fun CommentItem(
    postComment: PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape),
            model = postComment.authorAvatarUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = postComment.authorName,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.commentText,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.publicationDate,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
