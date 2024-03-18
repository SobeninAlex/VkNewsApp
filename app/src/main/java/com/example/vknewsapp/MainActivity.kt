package com.example.vknewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.vknewsapp.examples.ActivityResultTest
import com.example.vknewsapp.ui.MainScreen
import com.example.vknewsapp.ui.theme.VkNewsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsAppTheme {
                ActivityResultTest()
            }
        }
    }
}