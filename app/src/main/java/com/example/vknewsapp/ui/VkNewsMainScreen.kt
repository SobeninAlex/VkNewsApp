package com.example.vknewsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vknewsapp.MainViewModel
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.ui.theme.VkNewsAppTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val feedPost = mainViewModel.feedPost.observeAsState(FeedPost())

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = { selectedItemPosition.intValue = index },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = item.titleResId)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
    ) {
        PostCard(
            modifier = Modifier
                .padding(8.dp),
            feedPost = feedPost.value, //todo
            onLikeClickListener = mainViewModel::updateCount,
            onShareClickListener = mainViewModel::updateCount,
            onViewClickListener = mainViewModel::updateCount,
            onCommentClickListener = mainViewModel::updateCount,
        )
    }
}

@Preview
@Composable
private fun PreviewMainScreenLight() {
    VkNewsAppTheme(
        darkTheme = false
    ) {
//        MainScreen()
    }
}

@Preview
@Composable
private fun PreviewMainScreenDark() {
    VkNewsAppTheme(
        darkTheme = true
    ) {
//        MainScreen()
    }
}