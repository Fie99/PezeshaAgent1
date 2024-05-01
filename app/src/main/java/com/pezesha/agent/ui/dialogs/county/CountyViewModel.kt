package com.pezesha.agent.ui.dialogs.county

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.loan.usecase.GetCountiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CountyViewModel @Inject constructor(private val getCountiesUseCase: GetCountiesUseCase) :
    ViewModel() {

    fun getCountiesList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val res = getCountiesUseCase.execute()
            emit(res)
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

}