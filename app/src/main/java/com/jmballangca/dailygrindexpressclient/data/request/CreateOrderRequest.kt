package com.jmballangca.dailygrindexpressclient.data.request

import android.location.Location

data class CreateOrderRequest(
    val pickup_person : String,
    val phone_number : String,
    val location: String,
    val vehicle : String,
    val weight : Int,
)
