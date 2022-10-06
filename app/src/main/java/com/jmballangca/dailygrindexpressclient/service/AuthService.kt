package com.jmballangca.dailygrindexpressclient.service

import com.jmballangca.dailygrindexpressclient.data.response.CheckOtpResponse
import com.jmballangca.dailygrindexpressclient.data.response.CheckPhoneNumberResponse
import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.CustomerRegistrationResponse
import com.jmballangca.dailygrindexpressclient.utils.Constants.Companion.ROLE
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface AuthService {

    @GET("auth/check-phone-number")
    suspend fun checkPhoneNumber(@Query("phone_number") phoneNumber : String)  : Response<CheckPhoneNumberResponse?>

    @GET("auth/check-otp")
    suspend fun checkOtp(@Query("otp") otp : String) : Response<CheckOtpResponse?>

    @POST("auth/register")
    suspend fun customerRegistration(@Body customerRegistrationRequest: CustomerRegistrationRequest) : Response<CustomerRegistrationResponse?>


}