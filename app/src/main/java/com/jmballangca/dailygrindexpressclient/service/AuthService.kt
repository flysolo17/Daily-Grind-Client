package com.jmballangca.dailygrindexpressclient.service

import com.jmballangca.dailygrindexpressclient.data.request.CustomerRegistrationRequest
import com.jmballangca.dailygrindexpressclient.data.response.*
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @GET("auth/check-phone-number")
    suspend fun checkPhoneNumber(@Query("phone_number") phoneNumber : String)  : Response<CheckPhoneNumberResponse?>

    @GET("auth/check-otp")
    suspend fun checkOtp(@Query("otp") otp : String) : Response<CheckOtpResponse?>


    @POST("auth/register")
    suspend fun customerRegistration(@Body customerRegistrationRequest: CustomerRegistrationRequest) : Response<CustomerRegistrationResponse?>


    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("mobile_number") phone : String, @Field("password") password : String) : Response<LoginResponse?>

    @GET("user-profile")
    fun getProfile(@Header("Authorization") token : String) : Call<GetProfileResponse>

}