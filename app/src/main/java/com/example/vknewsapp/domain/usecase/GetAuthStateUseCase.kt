package com.example.vknewsapp.domain.usecase

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.repository.NewsFeedRepository

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() =
        repository.getAuthStateFlow()

}