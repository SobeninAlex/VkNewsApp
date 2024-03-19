package com.example.vknewsapp.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("source_id") val communityId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?,
)
