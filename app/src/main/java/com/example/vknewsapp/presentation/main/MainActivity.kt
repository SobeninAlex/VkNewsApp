package com.example.vknewsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsapp.domain.entity.AuthState
import com.example.vknewsapp.presentation.ViewModelFactory
import com.example.vknewsapp.presentation.VkNewsApplication
import com.example.vknewsapp.presentation.getApplicationComponent
import com.example.vknewsapp.ui.theme.VkNewsAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val component = getApplicationComponent()

            val mainViewModel = viewModel<MainViewModel>(
                factory = component.getViewModelFactory()
            )

            val authState = mainViewModel.authState.collectAsState(AuthState.Initial)

            val launcher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract()
            ) {
                mainViewModel.performAuthResult()
            }

            VkNewsAppTheme {

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