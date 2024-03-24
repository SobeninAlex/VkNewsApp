package com.example.vknewsapp.domain.usecase

import com.example.vknewsapp.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() =
        repository.getRecommendations()

}