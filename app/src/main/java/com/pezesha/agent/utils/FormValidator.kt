package com.pezesha.agent.utils

import android.util.Patterns
import com.pezesha.agent.utils.Constants.PHONE_REGEX

interface FormValidator {

    fun isValueValid(text: String): Pair<String?, Boolean>

    fun isNameValid(text: String): Pair<String?, Boolean>

    fun isEmailValid(text: String): Pair<String?, Boolean>

    fun isNinValid(text: String): Pair<String?, Boolean>

    fun isBizNameValid(text: String): Pair<String?, Boolean>

    fun isNumOfEmployeesValid(text: String): Pair<String?, Boolean>

//    fun isLoanAmountValid(text: String): Pair<String?, Boolean>

    fun isPhoneNumberValid(text: String): Pair<String?, Boolean>

    fun isOtpValid(text: String): Pair<String?, Boolean>
    fun isPinValid(text: String): Pair<String?, Boolean>

    fun isCodeValid(text: String): Pair<String?, Boolean>

}

class FormValidatorImpl : FormValidator {

    override fun isValueValid(text: String): Pair<String?, Boolean> {
        val value = text.trim()
        return if (value.isEmpty()) Pair("Input Required", false)
        else Pair(null, true)
    }

    override fun isNameValid(text: String): Pair<String?, Boolean> {
        val name = text.trim()

        return when {
            name.isEmpty() -> Pair("Input Required", false)
            name.length < 2 -> Pair("Invalid Name", false)
            else -> Pair(null, text.matches("^[A-Za-z]+$".toRegex()))
        }
    }

    override fun isEmailValid(text: String): Pair<String?, Boolean> {
        val email = text.trim()

        return when {
            email.isEmpty() -> Pair("Input Required", false)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair("Invalid email address", false)
            else -> Pair(null, true)
        }
    }

    override fun isNinValid(text: String): Pair<String?, Boolean> {
        val nin = text.trim()

        return when {
            nin.isEmpty() -> Pair("Input Required", false)
            nin.length < 6 -> Pair("Invalid National ID", false)
            else -> Pair(null, true)
        }
    }

    override fun isBizNameValid(text: String): Pair<String?, Boolean> {
        val name = text.trim()

        return when {
            name.isEmpty() -> Pair("Input Required", false)
            name.length < 2 -> Pair("Invalid Name", false)
            else -> Pair(null, true)
        }
    }

    override fun isNumOfEmployeesValid(text: String): Pair<String?, Boolean> {
        val name = text.trim()

        return when {
            name.isEmpty() -> Pair("Input Required", false)
            name.toInt() < 0 -> Pair("Invalid Number", false)
            else -> Pair(null, true)
        }
    }

//    override fun isLoanAmountValid(text: String): Pair<String?, Boolean> {
//        return when {
//            text.isEmpty() -> Pair("Input Required", false)
//            text.toInt() < MINIMUM_LOAN -> Pair("Try an Amount Higher than $MINIMUM_LOAN", false)
//            text.toInt() > GlobalVars.loanLimit -> Pair(
//                "Your Loan Limit is ${GlobalVars.loanLimit}",
//                false
//            )
//
//            else -> Pair(null, true)
//        }
//    }

    override fun isPhoneNumberValid(text: String): Pair<String?, Boolean> {
        val phoneNumber = text.trim()

        return when {
            phoneNumber.isEmpty() -> Pair("Input Required", false)
            !phoneNumber.matches(PHONE_REGEX.toRegex()) -> Pair("Invalid Phone Number", false)
            else -> Pair(null, true)
        }
    }

    override fun isOtpValid(text: String): Pair<String?, Boolean> {
        val otpCode = text.trim()

        return when {
            otpCode.isEmpty() -> Pair("Input Required", false)
            else -> Pair(null, true)
        }
    }

    override fun isPinValid(text: String): Pair<String?, Boolean> {
        val code = text.trim()

        return when {
            code.isEmpty() -> Pair("Input Required", false)
            code.length < 4 -> Pair("Invalid PIN", false)
            else -> Pair(null, true)
        }
    }

    override fun isCodeValid(text: String): Pair<String?, Boolean> {
        val code = text.trim()

        return when {
            code.isEmpty() -> Pair("Input Required", false)
            code.length < 6 -> Pair("Invalid Code", false)
            else -> Pair(null, true)
        }
    }

}
