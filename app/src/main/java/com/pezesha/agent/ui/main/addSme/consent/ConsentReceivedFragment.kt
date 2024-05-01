package com.pezesha.agent.ui.main.addSme.consent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentConsentReceivedBinding
import com.pezesha.agent.ui.main.addSme.AddSmeViewModel
import com.pezesha.agent.ui.main.addSme.AddSmeViewModel.Companion.KYC_STAGE
import com.pezesha.agent.ui.main.addSme.consent.ConsentPageState.COMPLETED
import com.pezesha.agent.ui.main.addSme.consent.ConsentPageState.ERROR
import com.pezesha.agent.ui.main.addSme.consent.ConsentPageState.LOADING
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsentReceivedFragment : Fragment() {
    private var _binding: FragmentConsentReceivedBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: GetConsentViewModel by activityViewModels()

    private val addSmeViewModel: AddSmeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyConsent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsentReceivedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initObservers()
    }

    private fun initObservers() {
        viewModel.consentPageState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> changeState(ERROR)
                is Resource.Loading -> changeState(LOADING)
                is Resource.Success -> changeState(COMPLETED)
            }
        }
    }

    private fun verifyConsent() {
        viewModel.verifyConsent()
    }

    private fun changeState(state: ConsentPageState) {
        binding.apply {
            when (state) {
                LOADING -> {
                    btnProceed.visibility = GONE
                    infoLabel.apply {
                        setLabelTitle(
                            title = getString(R.string.confirming_consent),
                            icon = ContextCompat.getDrawable(requireContext(), R.drawable.loading)
                        )
                        setContent(getString(R.string.wait_to_confirm_consent))
                    }
                }

                ERROR -> {
                    btnProceed.apply {
                        icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_retry)
                        visibility = VISIBLE
                        text = "Resend Consent"
                        setOnClickListener { verifyConsent() }
                    }
                    infoLabel.apply {
                        setLabelTitle(title = "", icon = null)
                        setContent("")
                    }
                }

                COMPLETED -> {
                    btnProceed.apply {
                        icon = ContextCompat.getDrawable(requireContext(), R.drawable.arrow_stick)
                        visibility = VISIBLE
                        text = getString(R.string.proceed)
                        setOnClickListener { proceed() }
                    }
                    infoLabel.apply {
                        setLabelTitle(
                            title = getString(R.string.consent_received),
                            icon = ContextCompat.getDrawable(requireContext(), R.drawable.thumb)
                        )
                        setContent(getString(R.string.consent_success_msg))
                    }
                }
            }
        }
    }

    private fun proceed() {
        addSmeViewModel.moveToStage(KYC_STAGE)
        navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

enum class ConsentPageState {
    LOADING, ERROR, COMPLETED
}
