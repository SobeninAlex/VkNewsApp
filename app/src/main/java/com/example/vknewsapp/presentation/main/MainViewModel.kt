package com.example.vknewsapp.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsapp.data.repository.NewsFeedRepository
import com.example.vknewsapp.presentation.news.NewsFeedScreenState
import com.example.vknewsapp.utils.extensions.mergeWith
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    val authState = repository.authStateFlow

    fun performAuthResult() {
        viewModelScope.launch {
            repository.checkAuthState()
        }
    }

}

