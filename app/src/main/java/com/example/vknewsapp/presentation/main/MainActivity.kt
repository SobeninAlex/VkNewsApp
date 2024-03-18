package com.example.vknewsapp.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsapp.ui.theme.VkNewsAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsAppTheme {

                val mainViewModel = viewModel<MainViewModel>()
                val authState = mainViewModel.authState.observeAsState(AuthState.Initial)
                var showSplashScreenState by remember {
                    mutableStateOf(true)
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    mainViewModel.performAuthResult(it)
                }

                LaunchedEffect(key1 = showSplashScreenState) {
                    delay(1500)
                    showSplashScreenState = false
                }

                if (showSplashScreenState) {
                    SplashScreen()
                } else {
                    when (authState.value) {
                        is AuthState.Authorized -> {
                            MainScreen()
                        }
                        is AuthState.NotAuthorized -> {
                            LoginScreen(
                                onLoginClick = {
                                    launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                                }
                            )
                        }
                        is AuthState.Initial -> {}
                    }
                }

            }
        }
    }
}