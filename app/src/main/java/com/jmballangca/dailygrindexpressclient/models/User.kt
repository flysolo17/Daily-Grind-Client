package com.jmballangca.dailygrindexpressclient.models

data class User(
    val avatar: List<Avatar>,
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