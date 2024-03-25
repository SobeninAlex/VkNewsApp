package com.example.vknewsapp.di.module

import androidx.lifecycle.ViewModel
import com.example.vknewsapp.di.annotation.ViewModelKey
import com.example.vknewsapp.presentation.comments.CommentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(impl: CommentsViewModel): ViewModel

}