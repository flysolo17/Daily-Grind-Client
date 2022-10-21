package com.jmballangca.dailygrindexpressclient.di

import android.content.Context
import com.jmballangca.dailygrindexpressclient.repository.auth.AuthRepository
import com.jmballangca.dailygrindexpressclient.repository.auth.AuthRepositoryImpl
import com.jmballangca.dailygrindexpressclient.repository.order.OrderRepository
import com.jmballangca.dailygrindexpressclient.repository.order.OrderRepositoryImpl
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.service.OrderService
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
    fun provideAuthRepository(
        @ApplicationContext context: Context
    ) : AuthRepository {
        return AuthRepositoryImpl(ApiInstance.api.create(AuthService::class.java),context)
    }

    @Singleton
    @Provides
    fun provideOrderRepository() : OrderRepository {
        return OrderRepositoryImpl(ApiInstance.api.create(OrderService::class.java))
    }

}