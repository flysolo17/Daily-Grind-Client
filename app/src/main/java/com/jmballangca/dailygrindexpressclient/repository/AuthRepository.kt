package com.jmballangca.dailygrindexpressclient.repository

import com.jmballangca.dailygrindexpressclient.data.response.CheckOtpResponse
import com.jmballangca.dailygrindexpressclient.data.response.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.CustomerRegistrationResponse
import com.jmballangca.dailygrindexpressclient.utils.UiState

interface AuthRepository {

    suspend fun checkPhoneNumber(phoneNumber : String,result: (UiState<CheckPhoneNumberResponse>) -> Unit )
    suspend fun checkOtp(otp : String,result: (UiState<CheckOtpResponse>) -> Unit)

    suspend fun customerRegistration(customerRegistrationRequest: CustomerRegistrationRequest,result: (UiState<CustomerRegistrationResponse>) -> Unit)

}