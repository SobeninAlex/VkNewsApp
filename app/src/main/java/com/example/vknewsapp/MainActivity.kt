package com.example.vknewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vknewsapp.ui.MainScreen
import com.example.vknewsapp.ui.PostCard
import com.example.vknewsapp.ui.theme.VkNewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewScreen()
        }
    }
}

@Composable
private fun PreviewPostCard() {
    VkNewsAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(8.dp),
        ) {
            PostCard()
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    VkNewsAppTheme {
        MainScreen()
    }
}

@Preview
@Composable
private fun PreviewScreenDark() {
    VkNewsAppTheme(
        darkTheme = true
    ) {
        MainScreen()
    }
}