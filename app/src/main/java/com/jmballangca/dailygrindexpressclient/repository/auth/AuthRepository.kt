package com.jmballangca.dailygrindexpressclient.repository.auth

import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.*
import com.jmballangca.dailygrindexpressclient.models.User
import com.jmballangca.dailygrindexpressclient.utils.UiState

interface AuthRepository {

    suspend fun checkPhoneNumber(phoneNumber : String,result: (UiState<CheckPhoneNumberResponse>) -> Unit )
    suspend fun checkOtp(otp : String,result: (UiState<CheckOtpResponse>) -> Unit)

    suspend fun customerRegistration(customerRegistrationRequest: CustomerRegistrationRequest,result: (UiState<CustomerRegistrationResponse>) -> Unit)

    suspend fun login(phone : String, password : String, result: (UiState<LoginResponse>) -> Unit)

    suspend fun setUser(data : LoginResponse)
    suspend fun getUser(key : String) : String?

    suspend fun userProfile(token : String,result: (UiState<User>) -> Unit)

    suspend fun logout()
}