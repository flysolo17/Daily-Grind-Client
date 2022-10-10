package com.jmballangca.dailygrindexpressclient.data.response

data class LoginData(
    val access_token: String,
    val access_token_expiration_timer_minutes: Int,
    val access_token_expires_at: String,
    val access_token_type: String,
    val user: User
)