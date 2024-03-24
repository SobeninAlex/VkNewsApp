package com.example.vknewsapp.di.module

import com.example.vknewsapp.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsapp.di.annotation.ApplicationScope
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

}