package com.jmballangca.dailygrindexpressclient.utils

import java.util.regex.Pattern

const val PREFERENCE_KEY = "user"
const val TOKEN = "users_token"

const val TOKEN_TYPE = "users_token_type"
const val USER_FULLNAME= "users_fullname"
const val USER_PROFILE = "users_profile"
const val ROLE = "customer"
const val CREATED = 201
const val OK = 200


val BANNED_CHARACTERS : Regex = "[1234567890~!@#\$%^&*()_+=`]".toRegex()
val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9+._%\\-]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

