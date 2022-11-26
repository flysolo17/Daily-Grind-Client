package com.jmballangca.dailygrindexpressclient.models

data class Order(
    val driver: String,
    val location: String,
    val phone_number: String,
    val pickup_person: String,
    val slug: String,
    val status: String,
    val total: Int,
    val vehicle_used: String,
    val weight: String
)