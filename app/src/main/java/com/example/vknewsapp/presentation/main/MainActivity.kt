package com.example.vknewsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsapp.ui.theme.VkNewsAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {

//    private val newsFeedViewModel by viewModels<NewsFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                newsFeedViewModel.isLoadingContent.value
//            } todo
        }

        setContent {
            VkNewsAppTheme {

                val mainViewModel = viewModel<MainViewModel>()
                val authState = mainViewModel.authState.observeAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    mainViewModel.performAuthResult(it)
                }

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