package com.pezesha.agent.ui.main.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.customers.usecase.GetCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.Sentry
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CustomersFragmentViewModel @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase
) : ViewModel() {

    fun getCustomersList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val res = getCustomersUseCase.execute()
            emit(res)
        } catch (e: Exception) {
            Sentry.captureException(e)
            emit(Resource.Error(e.message.toString()))
        }
    }

}