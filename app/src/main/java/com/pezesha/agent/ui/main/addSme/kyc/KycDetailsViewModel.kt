package com.pezesha.agent.ui.main.addSme.kyc

import androidx.lifecycle.ViewModel
import com.pezesha.agent.R
import com.pezesha.agent.ui.main.addSme.kyc.ProfileState.ACTIVE
import com.pezesha.agent.ui.main.addSme.kyc.ProfileState.DONE
import com.pezesha.agent.ui.main.addSme.kyc.ProfileState.INACTIVE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class KycDetailsViewModel : ViewModel() {

    private val _txtTitle: MutableStateFlow<Int> =
        MutableStateFlow(R.string.fill_in_customer_personal_details)
    val txtTitle: StateFlow<Int> = _txtTitle

    private val _personalStage: MutableStateFlow<ProfileStage> =
        MutableStateFlow(
            ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray,
            )
        )
    val personalStage: StateFlow<ProfileStage> = _personalStage

    private val _businessStage: MutableStateFlow<ProfileStage> =
        MutableStateFlow(
            ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray,
            )
        )
    val businessStage: StateFlow<ProfileStage> = _businessStage

    private val _kinStage: MutableStateFlow<ProfileStage> =
        MutableStateFlow(
            ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray
            )
        )
    val kinStage: StateFlow<ProfileStage> = _kinStage

    private val _profileStage = MutableStateFlow(0)
    val profileStage: StateFlow<Int> = _profileStage


    fun navigate(step: Int) {
        _profileStage.value = step
        when (step) {
            0 -> updatePersonalState(ACTIVE)
            1 ->updateKinState(ACTIVE)
            else -> updateBusinessState(ACTIVE)
        }
    }

    private fun updatePersonalState(state: ProfileState) {
        val profileStage = when (state) {
            INACTIVE -> ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray
            )

            ACTIVE -> {
                _txtTitle.value = R.string.fill_in_customer_personal_details
                //inactivate business and documents state
                updateKinState(INACTIVE)
                updateBusinessState(INACTIVE)
                ProfileStage(
                    imgRes = R.drawable.circle_active,
                    txtColor = android.R.color.black
                )
            }

            DONE ->
                ProfileStage(
                    imgRes = R.drawable.circle_done,
                    txtColor = R.color.text_gray,
                )
        }

        _personalStage.value = profileStage
    }

    private fun updateBusinessState(state: ProfileState) {
        val profileStage = when (state) {
            INACTIVE -> ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray
            )

            ACTIVE -> {
                _txtTitle.value = R.string.fill_in_biz_details

                // set personal state to done
                updateKinState(DONE)
                ProfileStage(imgRes = R.drawable.circle_active, txtColor = android.R.color.black)
            }

            DONE -> ProfileStage(imgRes = R.drawable.circle_done, txtColor = R.color.text_gray)
        }
        _businessStage.value = profileStage
    }

    private fun updateKinState(state: ProfileState) {
        val profileStage = when (state) {
            INACTIVE -> ProfileStage(
                imgRes = R.drawable.circle_inactive,
                txtColor = R.color.text_gray
            )

            ACTIVE -> {
                _txtTitle.value = R.string.fill_in_next_of_kin_details
                //inactivate kin state
                updateBusinessState(INACTIVE)
                // set business state to done
                updatePersonalState(DONE)
                ProfileStage(imgRes = R.drawable.circle_active, txtColor = android.R.color.black)
            }

            DONE -> ProfileStage(imgRes = R.drawable.circle_done, txtColor = R.color.text_gray)
        }
        _kinStage.value = profileStage
    }

}

enum class ProfileState {
    INACTIVE,
    ACTIVE,
    DONE
}

data class ProfileStage(
    var imgRes: Int,
    var txtColor: Int,
)

