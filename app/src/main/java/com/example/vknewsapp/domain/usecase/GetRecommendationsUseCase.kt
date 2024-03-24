package com.example.vknewsapp.domain.usecase

import com.example.vknewsapp.domain.repository.NewsFeedRepository

class GetRecommendationsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() =
        repository.getRecommendations()

}