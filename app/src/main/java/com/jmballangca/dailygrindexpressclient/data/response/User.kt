package com.jmballangca.dailygrindexpressclient.data.response

data class User(
    val avatar: Any,
    val created_at: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val permission: List<String>,
    val phone_number: String,
    val phone_number_verified_at: String,
    val profile_photo: String,
    val updated_at: String
)