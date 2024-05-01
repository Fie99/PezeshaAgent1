package com.pezesha.agent.ui.main.addSme.consent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.register.useCase.GetRequestConsentUseCase
import com.pezesha.agent.domain.register.useCase.GetVerifyConsentUseCase
import com.pezesha.agent.domain.user.usecase.GetUserCountryCode
import com.pezesha.agent.utils.FormValidator
import com.pezesha.agent.utils.extensions.formatPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
class GetConsentViewModel @Inject constructor(
    private val formValidator: FormValidator,
    private val getUserCountryCode: GetUserCountryCode,
    private val getRequestConsentUseCase: GetRequestConsentUseCase,
    private val getVerifyConsentUseCase: GetVerifyConsentUseCase
) :
    ViewModel() {

    private val _consentPageState = MutableLiveData<Resource<String>>()
    val consentPageState: LiveData<Resource<String>> = _consentPageState

    val userCountryCode: Flow<String> = flow {
        emit(getUserCountryCode.execute())
    }.flowOn(Dispatchers.IO)

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _phoneValid = MutableStateFlow(false)
    val phoneValid = _phoneValid.asStateFlow()

    private val _phoneError = MutableLiveData<String?>()
    val phoneError: LiveData<String?> = _phoneError

    private val _consentCode = MutableStateFlow("")
    val consentCode = _consentCode.asStateFlow()

    private val _consentCodeValid = MutableStateFlow(false)
    val consentCodeValid = _consentCodeValid.asStateFlow()

    private val _consentCodeError = MutableLiveData<String?>()
    val consentCodeError: LiveData<String?> = _consentCodeError

    val isSubmitEnabled: Flow<Boolean> =
        combine(_phoneValid, _consentCodeValid) { phoneValid, consentCodeValid ->
            return@combine phoneValid and consentCodeValid
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

    fun setConsentCode(consentCode: String) {
        _consentCode.value = consentCode
        val (errorMsg, isValid) = formValidator.isCodeValid(_consentCode.value)
        _consentCodeError.value = errorMsg
        _consentCodeValid.value = isValid
    }

    fun requestConsent() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            delay(1000) //todo remove
            val res = getRequestConsentUseCase.execute(phone.value)
            emit(res)
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun verifyConsent() = viewModelScope.launch(Dispatchers.IO) {
        _consentPageState.postValue(Resource.Loading())
        try {
            delay(1000) //todo remove
            val res = getVerifyConsentUseCase.execute(phone.value)
            _consentPageState.postValue(res)
        } catch (e: Exception) {
            _consentPageState.postValue(Resource.Error(e.message.toString()))
        }
    }

}
