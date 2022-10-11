package com.jmballangca.dailygrindexpressclient.di

import android.content.Context
import com.jmballangca.dailygrindexpressclient.repository.AuthRepository
import com.jmballangca.dailygrindexpressclient.repository.AuthRepositoryImpl
import com.jmballangca.dailygrindexpressclient.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideOtpRepository(
        @ApplicationContext context: Context
    ) : AuthRepository {
        return AuthRepositoryImpl(ApiInstance.api.create(AuthService::class.java),context)
    }

}