package com.jmballangca.dailygrindexpressclient.views.registration

import android.widget.EditText
import com.jmballangca.dailygrindexpressclient.utils.BANNED_CHARACTERS
import com.jmballangca.dailygrindexpressclient.utils.EMAIL_ADDRESS_PATTERN


fun validateName(edtName : EditText) : Boolean{
    val name = edtName.text.toString()
    return if (name.isEmpty()) {
        edtName.error = "enter name"
        false
    } else if (name.contains(BANNED_CHARACTERS)) {
        edtName.error = "Invalid name"
        false
    } else {
        true
    }
}

fun validateEmail(edtEmail : EditText) : Boolean{
    val email = edtEmail.text.toString()
    return if (email.isEmpty()) {
        edtEmail.error = "enter email"
        false
    } else if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()) {
        edtEmail.error = "invalid email"
        false
    } else {

        true
    }
}

fun validatePhoneNumber(edtPhone : EditText) : Boolean{
    val phone = edtPhone.text.toString()
    return if (phone.isEmpty()) {
        edtPhone.error = "enter phone"
        false
    } else if (!phone.startsWith("09") || phone.length != 11) {
        edtPhone.error = "Invalid phone"
        false
    } else {

        true
    }
}

fun validatePassword(edtPassword : EditText)  : Boolean{
    val password = edtPassword.text.toString()
    return if (password.isEmpty()) {
        edtPassword.error = "enter password"
        false
    } else if (password.length < 8) {
        edtPassword.error = "The password must contain at least 8 characters."
        false
    }
    else if (!password.contains("[A-Z]".toRegex())) {
        edtPassword.error = "The password must contain at least one uppercase."
        false
    }
    else if (!password.contains("[1234567890]".toRegex())) {
        edtPassword.error = "The password must contain at least one number."
        false
    }
    else {
        true
    }
}