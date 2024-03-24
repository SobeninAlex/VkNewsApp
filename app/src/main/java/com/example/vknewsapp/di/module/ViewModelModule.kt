package com.example.vknewsapp.di.module

import androidx.lifecycle.ViewModel
import com.example.vknewsapp.di.annotation.ViewModelKey
import com.example.vknewsapp.presentation.comments.CommentsViewModel
import com.example.vknewsapp.presentation.main.MainViewModel
import com.example.vknewsapp.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(impl: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(impl: CommentsViewModel): ViewModel

}