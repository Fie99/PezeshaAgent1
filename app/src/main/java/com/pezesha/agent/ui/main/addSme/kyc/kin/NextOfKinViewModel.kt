package com.pezesha.agent.ui.main.addSme.kyc.kin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pezesha.agent.domain.user.usecase.GetUserCountryCode
import com.pezesha.agent.utils.FormValidator
import com.pezesha.agent.utils.extensions.formatPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NextOfKinViewModel @Inject constructor(
    private val formValidator: FormValidator,
    private val getUserCountryCode: GetUserCountryCode,
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    private val _secondName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _relationShip = MutableStateFlow("")

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    val userCountryCode: Flow<String> = flow {
        emit(getUserCountryCode.execute())
    }.flowOn(Dispatchers.IO)

    private val _firstNameValid = MutableStateFlow(false)
    private val _secondNameValid = MutableStateFlow(false)
    private val _lastNameValid = MutableStateFlow(false)
    private val _relationShipValid = MutableStateFlow(false)
    private val _phoneValid = MutableStateFlow(false)

    private val _phoneError = MutableLiveData<String?>()
    val phoneError: LiveData<String?> = _phoneError

    private val _firstNameError = MutableLiveData<String?>()
    val firstNameError: LiveData<String?> get() = _firstNameError

    private val _secondNameError = MutableLiveData<String?>()
    val secondNameError: LiveData<String?> get() = _secondNameError

    private val _lastNameError = MutableLiveData<String?>()
    val lastNameError: LiveData<String?> get() = _lastNameError

    val isSubmitEnabled: Flow<Boolean> =
        combine(
            _firstNameValid,
            _secondNameValid,
            _lastNameValid,
            _relationShipValid,
            _phoneValid
        ) { booleans: Array<Boolean> ->
            for (itemValid in booleans) {
                if (!itemValid) return@combine false
            }
            return@combine true
        }.flowOn(Dispatchers.IO)

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

    fun setRelationShip(relationship: String) {
        _relationShip.value = relationship
        _relationShipValid.value = relationship.isNotEmpty()
    }

    fun setPhone(phone: String) {
        viewModelScope.launch {
            _phone.value =
                phone.formatPhoneNumber(userCountryCode.first().substring(1))
            val (errorMsg, isValid) = formValidator.isPhoneNumberValid(_phone.value)
            _phoneError.value = errorMsg
            _phoneValid.value = isValid
        }
    }

}