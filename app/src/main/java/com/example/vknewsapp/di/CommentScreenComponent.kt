package com.example.vknewsapp.di

import com.example.vknewsapp.di.annotation.ApplicationScope
import com.example.vknewsapp.di.module.CommentViewModelModule
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(
    modules = [
        CommentViewModelModule::class
    ]
)
interface CommentScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance feedPost: FeedPost,
        ) : CommentScreenComponent
    }

}