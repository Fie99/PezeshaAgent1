package com.pezesha.agent.ui.main.addSme

import androidx.lifecycle.ViewModel
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.ACTIVE
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.DONE
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.INACTIVE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddSmeViewModel : ViewModel() {

    private val _consentState = MutableStateFlow(INACTIVE)
    val consentState = _consentState.asStateFlow()

    private val _kycState = MutableStateFlow(INACTIVE)
    val kycState = _kycState.asStateFlow()

    private val _crbState = MutableStateFlow(INACTIVE)
    val crbState = _crbState.asStateFlow()

    private val _docsState = MutableStateFlow(INACTIVE)
    val docsState = _docsState.asStateFlow()

    private val _quizState = MutableStateFlow(INACTIVE)
    val quizState = _quizState.asStateFlow()

    private val _applyState = MutableStateFlow(INACTIVE)
    val applyState = _applyState.asStateFlow()

    private val _addSmeStage = MutableStateFlow(0)
    val addSmeStage = _addSmeStage.asStateFlow()

    init {
        moveToStage(CONSENT_STAGE)
    }

    fun moveToStage(smeStage: Int) {
        _addSmeStage.value = smeStage
        when (smeStage) {
            1 -> {
                _consentState.value = ACTIVE
                _kycState.value = INACTIVE
                _crbState.value = INACTIVE
                _docsState.value = INACTIVE
                _quizState.value = INACTIVE
                _applyState.value = INACTIVE
            }
            2 -> {
                _consentState.value = DONE
                _kycState.value = ACTIVE
                _crbState.value = INACTIVE
                _docsState.value = INACTIVE
                _quizState.value = INACTIVE
                _applyState.value = INACTIVE
            }
            3 -> {
                _consentState.value = DONE
                _kycState.value = DONE
                _crbState.value = ACTIVE
                _docsState.value = INACTIVE
                _quizState.value = INACTIVE
                _applyState.value = INACTIVE
            }

            4 -> {
                _consentState.value = DONE
                _kycState.value = DONE
                _crbState.value = DONE
                _docsState.value = ACTIVE
                _quizState.value = INACTIVE
                _applyState.value = INACTIVE
            }

            5 -> {
                _consentState.value = DONE
                _kycState.value = DONE
                _crbState.value = DONE
                _docsState.value = DONE
                _quizState.value = ACTIVE
                _applyState.value = INACTIVE
            }

            6 -> {
                _consentState.value = DONE
                _kycState.value = DONE
                _crbState.value = DONE
                _docsState.value = DONE
                _quizState.value = DONE
                _applyState.value = ACTIVE
            }

            else -> {
                _consentState.value = INACTIVE
                _kycState.value = INACTIVE
                _crbState.value = INACTIVE
                _docsState.value = INACTIVE
                _quizState.value = INACTIVE
                _applyState.value = INACTIVE
            }
        }
    }

    companion object {
        const val CONSENT_STAGE = 1
        const val KYC_STAGE = 2
        const val CRB_STAGE = 3
        const val DOCUMENTS_STAGE = 4
        const val QUESTIONS_STAGE = 5
        const val APPLY_LOAN_STAGE = 6
    }

}
