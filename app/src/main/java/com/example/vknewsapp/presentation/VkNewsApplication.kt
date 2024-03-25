package com.example.vknewsapp.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.vknewsapp.di.ApplicationComponent
import com.example.vknewsapp.di.DaggerApplicationComponent

class VkNewsApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    Log.d("RECOMPOSITION_TAG", "getApplicationComponent")
    return (LocalContext.current.applicationContext as VkNewsApplication).component
}