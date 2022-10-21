package com.jmballangca.dailygrindexpressclient.data.request

import android.location.Location

data class CreateOrderRequest(
    val pickup_person : String,
    val phone_number : String,
    val location: Any = "anywhere",
    val vehicle_used : String,
    val weight : Int,
)
