package com.example.vknewsapp.domain.entity

import com.example.vknewsapp.R

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val commentText: String,
    val publicationDate: String
)
