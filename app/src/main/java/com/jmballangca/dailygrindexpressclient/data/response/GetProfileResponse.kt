package com.jmballangca.dailygrindexpressclient.data.response

import com.jmballangca.dailygrindexpressclient.models.Profile
import com.jmballangca.dailygrindexpressclient.models.User

data class GetProfileResponse(
    val `data`: User
)