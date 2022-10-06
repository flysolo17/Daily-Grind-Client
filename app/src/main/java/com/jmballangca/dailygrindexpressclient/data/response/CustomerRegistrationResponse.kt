package com.jmballangca.dailygrindexpressclient.data.response

import android.location.Location
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class CustomerRegistrationResponse (
    val data: Data
) : Serializable


data class Data (
    val id: Long,
    val full_name: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val created_at: String,
    val updated_at: String,
    val user_location: Location? = null
)


