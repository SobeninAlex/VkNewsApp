package com.example.vknewsapp.domain.entity

sealed class AuthState {

    data object Initial: AuthState()

    data object Authorized: AuthState()

    data object NotAuthorized: AuthState()

}