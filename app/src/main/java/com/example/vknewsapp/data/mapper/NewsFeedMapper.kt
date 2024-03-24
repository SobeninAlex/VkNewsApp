package com.example.vknewsapp.data.mapper

import com.example.vknewsapp.data.model.CommentsResponseDto
import com.example.vknewsapp.data.model.NewsFeedResponseDto
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment
import com.example.vknewsapp.domain.entity.StatisticItem
import com.example.vknewsapp.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(newsFeedResponse: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = newsFeedResponse.newsFeedContent.posts
        val groups = newsFeedResponse.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                communityId = post.communityId,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistic = listOf(
                    StatisticItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticItem(type = StatisticType.VIEWS, count = post.views.count),
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }

        return result
    }

    fun napResponseToComments(commentsResponse: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()

        val comments = commentsResponse.commentsContent.comments
        val profiles = commentsResponse.commentsContent.profiles

        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val profile = profiles.find { it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${profile.firstName} ${profile.lastName}",
                authorAvatarUrl = profile.imageUrl,
                commentText = comment.text,
                publicationDate = mapTimestampToDate(comment.date)
            )
            result.add(postComment)
        }

        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }

}