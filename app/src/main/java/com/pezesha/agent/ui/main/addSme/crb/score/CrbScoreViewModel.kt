package com.pezesha.agent.ui.main.addSme.crb.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class CrbScoreViewModel: ViewModel() {

    private val _reasons = MutableStateFlow("")
    val reasons = _reasons.asStateFlow()

    fun retrieveReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val instr = StringBuilder()
            instr.append("&#8226; Low credit activity for assessment low.")
            instr.append(" <br />&#8226; High level of recent credit enquiry activity.")
            instr.append(" <br />&#8226; High installment delinquency in the last 24 months.")
            instr.append(" <br />&#8226; Low credit activity for assessment low.")
            _reasons.value = instr.toString()
        }
    }

}