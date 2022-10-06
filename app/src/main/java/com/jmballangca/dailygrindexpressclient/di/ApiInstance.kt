package com.jmballangca.dailygrindexpressclient.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



object ApiInstance {
    private const val BASE_URL = "https://staging.daily-grind.tatsing.com.ph/api/v1/"
    private val builder = OkHttpClient.Builder().build()
    val api: Retrofit by lazy {
            Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
                .client(builder)
            .build()
    }

}