package com.jmballangca.dailygrindexpressclient.repository

import com.jmballangca.dailygrindexpressclient.data.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.service.OtpService
import com.jmballangca.dailygrindexpressclient.utils.UiState
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class OtpRepositoryImpl(private val otpService: OtpService) : OtpRepository {
    override suspend fun checkPhoneNumber(
        phoneNumber: String,
        result: (UiState<CheckPhoneNumberResponse>) -> Unit
    ) {
        try {
            val response = otpService.checkPhoneNumber(phoneNumber)
            if (response.isSuccessful && response.body() != null) result.invoke(UiState.Success(response.body()!!))
        } catch (e : IOException) {
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e : HttpException) {
            result.invoke(UiState.Failure(message = e.message()))
        }
    }
}