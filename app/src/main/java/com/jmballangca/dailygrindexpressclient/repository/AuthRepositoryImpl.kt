package com.jmballangca.dailygrindexpressclient.repository

import com.jmballangca.dailygrindexpressclient.data.response.CheckOtpResponse
import com.jmballangca.dailygrindexpressclient.data.response.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.CustomerRegistrationResponse
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.utils.Constants
import com.jmballangca.dailygrindexpressclient.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun checkPhoneNumber(
        phoneNumber: String,
        result: (UiState<CheckPhoneNumberResponse>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        try {
            val response = authService.checkPhoneNumber(phoneNumber)
            if (response.isSuccessful && response.body() != null) result.invoke(
                UiState.Success(
                    response.body()!!
                )
            )
        } catch (e: IOException) {
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e: HttpException) {
            result.invoke(UiState.Failure(message = e.message()))
        }
    }

    override suspend fun checkOtp(otp: String, result: (UiState<CheckOtpResponse>) -> Unit) {
        result.invoke(UiState.Loading)
        try {
            val response = authService.checkOtp(otp)
            if (response.code() == 200) {
                result.invoke(UiState.Success(response.body()!!))
            } else {
                result.invoke(UiState.Failure(message = "Failed"))
            }

        } catch (e: IOException) {
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e: HttpException) {
            result.invoke(UiState.Failure(message = e.message()))
        }
    }

    override suspend fun customerRegistration(
        customerRegistrationRequest: CustomerRegistrationRequest,
        result: (UiState<CustomerRegistrationResponse>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        try {
            val response = authService.customerRegistration(customerRegistrationRequest)
            if (response.isSuccessful && response.code() == 201) {
                result.invoke(UiState.Success(response.body()!!))
            } else {
                result.invoke(UiState.Failure(message = "${response.code()} ${response.body()}"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e: HttpException) {
            result.invoke(UiState.Failure(message = e.message()))
        }
    }


}