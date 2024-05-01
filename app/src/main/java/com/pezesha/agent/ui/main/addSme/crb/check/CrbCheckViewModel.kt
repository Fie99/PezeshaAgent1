package com.pezesha.agent.ui.main.addSme.crb.check

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
class CrbCheckViewModel @Inject constructor(
    private val formValidator: FormValidator,
    private val getUserCountryCode: GetUserCountryCode,
): ViewModel() {

    val userCountryCode: Flow<String> = flow {
        emit(getUserCountryCode.execute())
    }.flowOn(Dispatchers.IO)

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _phoneValid = MutableStateFlow(false)
    val phoneValid = _phoneValid.asStateFlow()

    private val _phoneError = MutableLiveData<String?>()
    val phoneError: LiveData<String?> = _phoneError

    private val _hasCheckedCrb = MutableStateFlow(false)

    val isSubmitEnabled: Flow<Boolean> =
        combine(_phoneValid, _hasCheckedCrb) { phoneValid, hasCheckedCrb ->
            return@combine phoneValid and hasCheckedCrb
        }.flowOn(Dispatchers.IO)

    fun setPhone(phone: String) {
        viewModelScope.launch {
            _phone.value =
                phone.formatPhoneNumber(userCountryCode.first().substring(1))
            val (errorMsg, isValid) = formValidator.isPhoneNumberValid(_phone.value)
            _phoneError.value = errorMsg
            _phoneValid.value = isValid
        }
    }



    fun performCrbCheck() {
        _hasCheckedCrb.value = true
    }

}