package com.example.vknewsapp.presentation

import android.app.Application
import com.example.vknewsapp.di.ApplicationComponent
import com.example.vknewsapp.di.DaggerApplicationComponent

class VkNewsApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

}