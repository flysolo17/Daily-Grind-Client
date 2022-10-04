package com.jmballangca.dailygrindexpressclient.service

import com.jmballangca.dailygrindexpressclient.data.CheckPhoneNumberResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {
    @GET("auth/check-phone-number")
    suspend fun checkPhoneNumber(@Query("phone_number") phoneNumber : String)  : Response<CheckPhoneNumberResponse?>
}