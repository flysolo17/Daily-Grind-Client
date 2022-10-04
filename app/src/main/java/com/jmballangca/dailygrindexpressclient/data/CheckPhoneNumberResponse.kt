package com.jmballangca.dailygrindexpressclient.data

data class CheckPhoneNumberResponse(
    val otp : String,
    val msg : String,
    val status : Int
)