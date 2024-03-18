package com.example.vknewsapp.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> get() = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        Log.d("MainViewModelTag", "access_token: ${token?.accessToken}")

        _authState.value = if (loggedIn) {
            AuthState.Authorized
        } else {
            AuthState.NotAuthorized
        }
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Success -> {
                _authState.value = AuthState.Authorized
            }
            is VKAuthenticationResult.Failed -> {
                Log.d("MainViewModelTag", result.exception.toString())
                _authState.value = AuthState.NotAuthorized
            }
        }
    }

}

