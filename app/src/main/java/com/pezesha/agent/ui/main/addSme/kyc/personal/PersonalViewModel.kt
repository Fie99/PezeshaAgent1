package com.pezesha.agent.ui.main.addSme.kyc.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pezesha.agent.utils.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val formValidator: FormValidator) :
    ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String>
        get() = _firstName

    private val _secondName = MutableStateFlow("")
    val secondName: StateFlow<String>
        get() = _secondName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _nationalId = MutableStateFlow(0.0)
    val nationalId: StateFlow<Double>
        get() = _nationalId

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String>
        get() = _dob

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String>
        get() = _gender

    private val _educationLevel = MutableStateFlow("")
    val educationLevel: StateFlow<String> get() = _educationLevel

    private val _firstNameValid = MutableStateFlow(false)
    private val _secondNameValid = MutableStateFlow(false)
    private val _lastNameValid = MutableStateFlow(false)
    private val _emailValid = MutableStateFlow(false)
    private val _nationalIdValid = MutableStateFlow(false)
    private val _dobValid = MutableStateFlow(false)
    private val _genderValid = MutableStateFlow(false)
    private val _educationLevelValid = MutableStateFlow(false)

    private val _firstNameError = MutableLiveData<String?>()
    val firstNameError: LiveData<String?> get() = _firstNameError

    private val _secondNameError = MutableLiveData<String?>()
    val secondNameError: LiveData<String?> get() = _secondNameError

    private val _lastNameError = MutableLiveData<String?>()
    val lastNameError: LiveData<String?> get() = _lastNameError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _nationalIdError = MutableLiveData<String?>()
    val nationalIdError: LiveData<String?> get() = _nationalIdError

    val isSubmitEnabled: Flow<Boolean> =
        combine(
            _firstNameValid,
            _secondNameValid,
            _lastNameValid,
//            _emailValid,
            _nationalIdValid,
            _dobValid,
//            _genderValid,
//            _educationLevelValid
        ) { booleans: Array<Boolean> ->
            for (itemValid in booleans) {
                if (!itemValid) return@combine false
            }
            return@combine true
        }

    fun setFirstName(firstName: String) {
        _firstName.value = firstName
        val (errorMsg, isValid) = formValidator.isNameValid(firstName)
        _firstNameError.value = errorMsg
        _firstNameValid.value = isValid
    }

    fun setSecondName(secondName: String) {
        _secondName.value = secondName
        val (errorMsg, isValid) = formValidator.isNameValid(secondName)
        _secondNameError.value = errorMsg
        _secondNameValid.value = isValid
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
        val (errorMsg, isValid) = formValidator.isNameValid(lastName)
        _lastNameError.value = errorMsg
        _lastNameValid.value = isValid
    }

//    fun setEmail(email: String) {
//        _email.value = email
//        val (errorMsg, isValid) = formValidator.isEmailValid(email)
//        _emailError.value = errorMsg
//        _emailValid.value = isValid
//    }

    fun setNationalId(nationalId: String) {
        val str = nationalId.trim()
        str.filter { it.isDigit() }
        if (str.isNotEmpty()) {
            _nationalId.value = str.toDouble()
        }
        val (errorMsg, isValid) = formValidator.isNinValid(str)
        _nationalIdError.value = errorMsg
        _nationalIdValid.value = isValid
    }

    fun setDob(dob: String) {
        _dob.value = dob.trim()
        _dobValid.value = true
    }

//    fun setGender(gender: String) {
//        _gender.value = gender.trim()
//        _genderValid.value = true
//    }

    fun setFacebookProfile(facebookProfile: String) {
//        _educationLevel.value = educationLevel.trim()
//        _educationLevelValid.value = true
    }

    fun setTwitterHandle(twitterHandle: String) {
//        _educationLevel.value = educationLevel.trim()
//        _educationLevelValid.value = true
    }

    fun setInstagramUsername(instagramUsername: String) {
//        _educationLevel.value = educationLevel.trim()
//        _educationLevelValid.value = true
    }

    fun setSocialMediaAccount(string: String) {

    }

}
