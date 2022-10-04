package com.jmballangca.dailygrindexpressclient.repository

import com.jmballangca.dailygrindexpressclient.data.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.utils.UiState
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun checkPhoneNumber(
        phoneNumber: String,
        result: (UiState<CheckPhoneNumberResponse>) -> Unit
    ) {
        try {
            val response = authService.checkPhoneNumber(phoneNumber)
            if (response.isSuccessful && response.body() != null) result.invoke(UiState.Success(response.body()!!))
        } catch (e : IOException) {
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e : HttpException) {
            result.invoke(UiState.Failure(message = e.message()))
        }
    }
}