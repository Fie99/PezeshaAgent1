package com.pezesha.agent.ui.main.addSme.kyc.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pezesha.agent.data.models.BizSectorsResponse
import com.pezesha.agent.data.models.CountiesResponse
import com.pezesha.agent.data.models.SubCountiesResponse
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.loan.usecase.GetBizSectorsUseCase
import com.pezesha.agent.domain.loan.usecase.GetCountiesUseCase
import com.pezesha.agent.domain.loan.usecase.GetSubCountiesUseCase
import com.pezesha.agent.utils.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.Sentry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val formValidator: FormValidator,
    private val getBizSectorsUseCase: GetBizSectorsUseCase,
    private val getCountiesUseCase: GetCountiesUseCase,
    private val getSubCountiesUseCase: GetSubCountiesUseCase,
) : ViewModel() {


    private val _bizName = MutableStateFlow("")
    val bizName: StateFlow<String>
        get() = _bizName
    private val _numOfEmployees = MutableStateFlow(0)
    val numOfEmployees: StateFlow<Int>
        get() = _numOfEmployees
    private val _bizStartDate = MutableStateFlow("")
    val bizStartDate: StateFlow<String>
        get() = _bizStartDate
    private val _bizType = MutableStateFlow("")
    val bizType: StateFlow<String>
        get() = _bizType
    private val _bizSector = MutableStateFlow("")
    val bizSector: StateFlow<String>
        get() = _bizSector
    private val _bizCounty = MutableStateFlow("")
    val bizCounty: StateFlow<String>
        get() = _bizCounty
    private val _bizSubCounty = MutableStateFlow("")
    val bizSubCounty: StateFlow<String>
        get() = _bizSubCounty

    private val _bizNameValid = MutableStateFlow(false)
    private val _numOfEmployeesValid = MutableStateFlow(false)
    private val _bizStartDateValid = MutableStateFlow(false)
    private val _bizTypeValid = MutableStateFlow(false)
    private val _bizSectorValid = MutableStateFlow(false)
    private val _bizCountyValid = MutableStateFlow(false)
    private val _bizSubCountyValid = MutableStateFlow(false)

    private val _bizNameError = MutableLiveData<String?>()
    val bizNameError: LiveData<String?> get() = _bizNameError

    private val _numOfEmployeesError = MutableLiveData<String?>()
    val numOfEmployeesError: LiveData<String?> get() = _numOfEmployeesError

    private val _businessIndustries: MutableLiveData<Resource<BizSectorsResponse>> =
        MutableLiveData()
    val businessIndustries: LiveData<Resource<BizSectorsResponse>> = _businessIndustries
    private val _counties: MutableLiveData<Resource<CountiesResponse>> = MutableLiveData()
    val counties: LiveData<Resource<CountiesResponse>> = _counties
    private val _subCounties: MutableLiveData<Resource<SubCountiesResponse>> = MutableLiveData()
    val subCounties: LiveData<Resource<SubCountiesResponse>> = _subCounties


    val isSubmitEnabled: Flow<Boolean> =
        combine(
            _bizNameValid,
            _numOfEmployeesValid,
            _bizStartDateValid,
            _bizTypeValid,
            _bizSectorValid,
            _bizCountyValid,
            _bizSubCountyValid
        ) { booleans: Array<Boolean> ->
            for (itemValid in booleans) {
                if (!itemValid) return@combine false
            }
            return@combine true
        }

    fun setBizName(bizName: String) {
        _bizName.value = bizName
        val (errorMsg, isValid) = formValidator.isBizNameValid(bizName)
        _bizNameError.value = errorMsg
        _bizNameValid.value = isValid
    }

    fun setNumOfEmployees(numOfEmployees: String) {
        _numOfEmployees.value = if (numOfEmployees.isEmpty()) 0 else numOfEmployees.toInt()
        val (errorMsg, isValid) = formValidator.isNumOfEmployeesValid(numOfEmployees)
        _numOfEmployeesError.value = errorMsg
        _numOfEmployeesValid.value = isValid
    }

    fun setBizStartDate(startDate: String) {
        _bizStartDate.value = startDate
        _bizStartDateValid.value = true
    }

    fun setBizType(bizType: String) {
        _bizType.value = bizType
        _bizTypeValid.value = true
    }

    fun setBizSector(bizSector: String) {
        _bizSector.value = bizSector.trim()
        _bizSectorValid.value = true
    }

    fun setBizCounty(bizCounty: String) {
        _bizCounty.value = bizCounty.trim()
        _bizCountyValid.value = true
    }

    fun setBizSubCounty(bizSubCounty: String) {
        _bizSubCounty.value = bizSubCounty.trim()
        _bizSubCountyValid.value = true
    }

    fun getBusinessIndustries() = viewModelScope.launch(Dispatchers.IO) {
        _businessIndustries.postValue(Resource.Loading())
        try {
            val response = getBizSectorsUseCase.execute()
            _businessIndustries.postValue(response)
        } catch (e: Exception) {
            Sentry.captureException(e)
            _businessIndustries.postValue(Resource.Error("Something Wrong Happened"))
        }
    }

    fun getCountiesList() = viewModelScope.launch(Dispatchers.IO) {
        _counties.postValue(Resource.Loading())
        try {
            val countiesResponse = getCountiesUseCase.execute()
            _counties.postValue(countiesResponse)
        } catch (e: Exception) {
            Sentry.captureException(e)
            _counties.postValue(Resource.Error("Something Wrong Happened"))
        }
    }

    fun getSubCountiesList(countyCode: String = "") = viewModelScope.launch(Dispatchers.IO) {
        _subCounties.postValue(Resource.Loading())
        try {
            val subCountiesResponse = getSubCountiesUseCase.execute()
            _subCounties.postValue(subCountiesResponse)
        } catch (e: Exception) {
            Sentry.captureException(e)
            _subCounties.postValue(Resource.Error("Something Wrong Happened"))
        }
    }

}