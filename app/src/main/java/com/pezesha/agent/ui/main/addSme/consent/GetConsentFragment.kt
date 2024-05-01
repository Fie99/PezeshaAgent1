package com.pezesha.agent.ui.main.addSme.consent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentGetConsentBinding
import com.pezesha.agent.utils.GlobalMethods
import com.pezesha.agent.utils.extensions.showBalloon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class GetConsentFragment : Fragment() {

    private var _binding: FragmentGetConsentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: GetConsentViewModel by activityViewModels()

    @Inject
    lateinit var globalMethods: GlobalMethods

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetConsentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initObservers()
        initListeners()
        initUI()
    }

    private fun initListeners() {
        binding.apply {
            etPhoneNumber.doAfterTextChanged { viewModel.setPhone(it.toString()) }
            etConsentCode.doAfterTextChanged { viewModel.setConsentCode(it.toString()) }
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }
    }

    private fun initObservers() {
        binding.apply {
            lifecycleScope.launch {
                viewModel.userCountryCode.collect {
                    tlPhoneNumber.prefixText = it
                }
            }

            lifecycleScope.launch {
                viewModel.phoneValid.collect {
                    btnAskConsent.isEnabled = it
                    if (it) etPhoneNumber.imeOptions = EditorInfo.IME_ACTION_SEND
                    else etPhoneNumber.imeOptions = EditorInfo.IME_ACTION_NEXT
                }
            }

            lifecycleScope.launch {
                viewModel.isSubmitEnabled.collect {
                    btnConfirm.isEnabled = it
                }
            }

            viewModel.phoneError.observe(viewLifecycleOwner) {
                tlPhoneNumber.error = it
            }

            viewModel.consentCodeError.observe(viewLifecycleOwner) {
                tlConsentCode.error = it
            }

        }

    }

    private fun initUI() {
        binding.apply {
            btnConfirm.setOnClickListener {
                sendConsent()
            }

            btnAskConsent.setOnClickListener {
                askConsent()
            }
        }
    }

    private fun askConsent() {
        viewModel.requestConsent().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    globalMethods.toggleLoader(requireContext(), 0)
                    globalMethods.toggleError(requireContext(), "Sending Request...") {}
                }

                is Resource.Loading -> {
                    globalMethods.toggleLoader(requireContext(), 1)
                }

                is Resource.Success -> {
                    globalMethods.toggleLoader(requireContext(), 0)
                    parseSuccess(resource.data ?: "")
                }
            }
        }
    }

    private fun parseSuccess(data: String) {
        binding.infoLabel.showBalloon("SMS Sent to Customer.")
        binding.etConsentCode.isEnabled = true
    }

    private fun sendConsent() {
        navController.navigate(R.id.action_getConsentFragment_to_consentReceivedFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
