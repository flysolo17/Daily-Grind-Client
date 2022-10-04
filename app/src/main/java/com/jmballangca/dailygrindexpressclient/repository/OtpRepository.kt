package com.jmballangca.dailygrindexpressclient.repository

import com.jmballangca.dailygrindexpressclient.data.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.utils.UiState

interface OtpRepository {
    suspend fun checkPhoneNumber(phoneNumber : String,result: (UiState<CheckPhoneNumberResponse>) -> Unit )
}