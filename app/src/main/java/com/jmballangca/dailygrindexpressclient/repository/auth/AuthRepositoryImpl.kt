package com.jmballangca.dailygrindexpressclient.repository.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.*
import com.jmballangca.dailygrindexpressclient.di.ApiInstance.dataStore
import com.jmballangca.dailygrindexpressclient.models.User
import com.jmballangca.dailygrindexpressclient.service.AuthService
import com.jmballangca.dailygrindexpressclient.utils.*
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback

import retrofit2.HttpException
import retrofit2.Response

import java.io.IOException



class AuthRepositoryImpl(private val authService: AuthService ,private val context: Context) :
    AuthRepository {
    private var user : User? = null

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
                response.body()?.let {
                    user = it.data.user
                }
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

    override suspend fun setUser(data : LoginResponse) {
        val token = stringPreferencesKey(name = TOKEN)
        val tokenType = stringPreferencesKey(name = TOKEN_TYPE)
        val userFullname = stringPreferencesKey(name = USER_FULLNAME)
        val userProfile = stringPreferencesKey(name = USER_PROFILE)
        context.dataStore.edit { user ->
            user[token] = data.data.access_token
            user[tokenType] = data.data.access_token_type
            user[userFullname] = data.data.user.full_name
            user[userProfile] = data.data.user.profile_photo
        }
    }

    override suspend fun getUser(key : String): String? {
        val data = stringPreferencesKey(name = key)
        val token = context.dataStore.data.first()
        return token[data]
    }



    override suspend fun logout() {
        context.dataStore.edit { user ->
            user.clear()
        }
    }
    override suspend fun userProfile(
        token: String,
        result: (UiState<User>) -> Unit
    ) {
        user?.let {
            result.invoke(UiState.Success(it))
            return
        }
        result.invoke(UiState.Loading)
        authService.getProfile(token).enqueue(object  : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                if (response.isSuccessful && response.code() == OK && response.body() != null) {
                    response.body()?.let {
                        result.invoke(UiState.Success(it.data))
                        user = it.data
                    }

                    return
                }
                result.invoke(UiState.Failure("Failed getting profile!"))
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                result.invoke(UiState.Failure(t.message.toString()))
            }

        })
    }


}