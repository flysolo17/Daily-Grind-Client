package com.jmballangca.dailygrindexpressclient.di

import com.jmballangca.dailygrindexpressclient.repository.AuthRepository
import com.jmballangca.dailygrindexpressclient.repository.AuthRepositoryImpl
import com.jmballangca.dailygrindexpressclient.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideOtpRepository() : AuthRepository {
        return AuthRepositoryImpl(ApiInstance.api.create(AuthService::class.java))
    }
}