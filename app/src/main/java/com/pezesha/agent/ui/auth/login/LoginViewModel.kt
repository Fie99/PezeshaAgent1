package com.pezesha.agent.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pezesha.agent.PezeshaAgentApp
import com.pezesha.agent.data.remote.utils.NoConnectivityException
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.auth.usecase.GetLoginUseCase
import com.pezesha.agent.utils.Constants.DEFAULT_COUNTRY_CODE
import com.pezesha.agent.utils.FormValidator
import com.pezesha.agent.utils.extensions.formatPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.Sentry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val formValidator: FormValidator,
    private val getLoginUseCase: GetLoginUseCase
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    private val _phoneValid = MutableStateFlow(false)
    private val _phoneError: MutableLiveData<String?> = MutableLiveData()
    val phoneError: LiveData<String?>
        get() = _phoneError

    private val _pin = MutableStateFlow("")
    private val _pinValid = MutableStateFlow(false)
    private val _pinError: MutableLiveData<String?> = MutableLiveData()
    val pinError: LiveData<String?>
        get() = _pinError

    private val _countryCode = MutableStateFlow(DEFAULT_COUNTRY_CODE)

    init {
        PezeshaAgentApp.sharedPrefsManager.countryCode = "+254"
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_phoneValid, _pinValid) { phoneValid, pinValid ->
        return@combine phoneValid and pinValid
    }.flowOn(Dispatchers.IO)

    fun setCountryCode(countryCode: String) {
        _countryCode.value = countryCode
        PezeshaAgentApp.sharedPrefsManager.countryCode = countryCode
        checkPhone()
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber.formatPhoneNumber(_countryCode.value.substring(1))
        checkPhone()
    }

    private fun checkPhone() {
        val (errorMsg, isValid) = formValidator.isPhoneNumberValid(_phoneNumber.value)
        _phoneError.value = errorMsg
        _phoneValid.value = isValid
    }

    fun setPin(pin: String) {
        _pin.value = pin
        val (errorMsg, isValid) = formValidator.isPinValid(_pin.value)
        _pinError.value = errorMsg
        _pinValid.value = isValid
    }

    fun login() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            val response = getLoginUseCase.execute(_countryCode.value)
            emit(response)
        } catch (e: NoConnectivityException) {
            emit(Resource.Error("No Internet Connection"))
        } catch (e: Exception) {
            Sentry.captureException(e)
            emit(Resource.Error("Something Wrong Happened"))
        }
    }


}
