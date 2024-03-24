package com.example.vknewsapp.domain.usecase

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke() =
        repository.getAuthStateFlow()

}