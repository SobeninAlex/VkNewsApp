package com.example.vknewsapp.domain

import com.example.vknewsapp.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author Name",
    val authorAvatarId: Int = R.drawable.ic_account_circle,
    val commentText: String = "Long comment",
    val publicationDate: String = "14:00"
)
