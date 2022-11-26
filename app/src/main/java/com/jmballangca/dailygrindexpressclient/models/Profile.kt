package com.jmballangca.dailygrindexpressclient.models

data class Profile(
    val created_at: String,
    val email: String,
    val first_name: String,
    val full_name: String,
    val id: Int,
    val last_name: String,
    val permissions: List<String>,
    val phone_number: String,
    val role_name: String,
    val updated_at: String,
    val user_location: Any
)