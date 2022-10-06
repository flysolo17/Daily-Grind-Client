package com.jmballangca.dailygrindexpressclient.data.request

import retrofit2.http.Field


data class CustomerRegistrationRequest(
    val otp : String,
    val first_name : String,
    val last_name : String,
    val email : String,
    val password : String,
    val password_confirmation : String,
    val role : String,
    val phone_number : String
)
