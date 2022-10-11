package com.jmballangca.dailygrindexpressclient.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jmballangca.dailygrindexpressclient.data.response.CheckOtpResponse
import com.jmballangca.dailygrindexpressclient.data.response.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.CustomerRegistrationResponse
import com.jmballangca.dailygrindexpressclient.data.response.LoginResponse
import com.jmballangca.dailygrindexpressclient.di.ApiInstance.dataStore
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.utils.*
import kotlinx.coroutines.flow.first

import retrofit2.HttpException

import java.io.IOException



class AuthRepositoryImpl(private val authService: AuthService ,private val context: Context) : AuthRepository {
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
            if (response.code() == CREATED) {
                result.invoke(UiState.Success(response.body()!!))
            } else {
                result.invoke(UiState.Failure(message = "${response.code()} ${response.body()}"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e: HttpException) {
            e.printStackTrace()
            result.invoke(UiState.Failure(message = e.message()))
        }
    }


    override suspend fun login(
        phone: String,
        password: String,
        result: (UiState<LoginResponse>) -> Unit
    ) {
        result.invoke(UiState.Loading)
        try {
            val response = authService.login(phone,password)
            if (response.isSuccessful && response.code() == OK) {
                result.invoke(UiState.Success(response.body()!!))
            } else {
                result.invoke(UiState.Failure(message = "Invalid email or password"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            result.invoke(UiState.Failure(message = e.message!!))
        } catch (e: HttpException) {
            e.printStackTrace()
            result.invoke(UiState.Failure(message = e.message()))
        }
    }

    override suspend fun setUser(token: String, tokenType: String) {
       val key = stringPreferencesKey(name = TOKEN)
        context.dataStore.edit { user ->
            user[key] = "$tokenType $token"
        }

    }

    override suspend fun getUser(): String? {
        val key = stringPreferencesKey(name = TOKEN)
        val token = context.dataStore.data.first()
        return token[key]
    }

    override suspend fun logout() {
        context.dataStore.edit { user ->
            user.clear()
        }
    }


}