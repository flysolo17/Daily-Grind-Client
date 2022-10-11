package com.jmballangca.dailygrindexpressclient.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jmballangca.dailygrindexpressclient.utils.PREFERENCE_KEY
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

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_KEY)



}