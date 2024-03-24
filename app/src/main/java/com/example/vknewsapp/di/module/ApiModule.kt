package com.example.vknewsapp.di.module

import android.content.Context
import com.example.vknewsapp.data.network.ApiService
import com.example.vknewsapp.di.annotation.ApplicationScope
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
object ApiModule {

    @Provides
    @ApplicationScope
    fun provideVKPreferencesKeyValueStorage(context: Context) =
        VKPreferencesKeyValueStorage(context)

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://api.vk.com/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @ApplicationScope
    fun provideApiService(retrofit: Retrofit) = retrofit.create<ApiService>()

}