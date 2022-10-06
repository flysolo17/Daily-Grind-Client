package com.jmballangca.dailygrindexpressclient.data.response

data class CheckPhoneNumberResponse(
    val otp : String,
    val msg : String,
    val status : Int
)