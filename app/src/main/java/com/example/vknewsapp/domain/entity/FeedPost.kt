package com.example.vknewsapp.domain.entity

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

/**
 * @Immutable -> говорим компилятору, что экземпляры класса FeedPost 100% НЕ изменяемые!
 * Если какая-то composable-функция принимает в качестве параметра объект FeedPost, и эта же функция
 * вызывается еще раз с тем же объектом, то производить рекомпозицию НЕ нужно.
 * Данной аннотацией НЕ следует помечать классы если у них есть поля var
 */

@Immutable
@Parcelize
data class FeedPost(
    val id: Long,
    val communityName: String,
    val communityId: Long,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistic: List<StatisticItem>,
    val isLiked: Boolean,
) : Parcelable {

    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }
        }

    }

}