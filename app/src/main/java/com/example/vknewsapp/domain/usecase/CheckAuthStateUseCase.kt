package com.example.vknewsapp.domain.usecase

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() =
        repository.checkAuthState()

}