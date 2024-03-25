package com.example.vknewsapp.di

import android.content.Context
import com.example.vknewsapp.di.annotation.ApplicationScope
import com.example.vknewsapp.di.module.ApiModule
import com.example.vknewsapp.di.module.DataModule
import com.example.vknewsapp.di.module.ViewModelModule
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ApiModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun getCommentScreenComponentFactory(): CommentScreenComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }

}