package com.jmballangca.dailygrindexpressclient.di

import com.jmballangca.dailygrindexpressclient.repository.OtpRepository
import com.jmballangca.dailygrindexpressclient.repository.OtpRepositoryImpl
import com.jmballangca.dailygrindexpressclient.service.OtpService
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
    fun provideOtpRepository() : OtpRepository {
        return OtpRepositoryImpl(ApiInstance.api.create(OtpService::class.java))
    }
}