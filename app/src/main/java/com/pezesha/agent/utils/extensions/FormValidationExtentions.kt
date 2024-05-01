package com.pezesha.agent.utils.extensions

import android.util.Patterns
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.isEmpty(): Boolean {
    return text.toString().trim().isEmpty()
}

fun TextInputEditText.isValidName(): Boolean {
    val text = text.toString()
    return text.isNotEmpty() && text.length > 2 && text.matches("^[A-Za-z]+$".toRegex())
}

fun TextInputEditText.isInvalidEmail(): Boolean {
    return !Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
}

fun TextInputEditText.isInvalidPhoneNumber(): Boolean {
    return text.toString().length < 9
}

fun TextInputEditText.isInvalidPhoneNumberMpesaPayment(): Boolean {
    return text.toString().length < 10 || text.toString().length > 10
}

fun TextInputEditText.isInvalidSecurityPin(): Boolean {
    return text.toString().length < 4
}

fun TextInputEditText.isInvalidSecurityPinConfirmation(securityPin: String): Boolean {
    return !(text.toString().equals(securityPin))
}


fun MaterialAutoCompleteTextView.isEmpty(): Boolean {
    return text.toString().trim().isEmpty()
}
