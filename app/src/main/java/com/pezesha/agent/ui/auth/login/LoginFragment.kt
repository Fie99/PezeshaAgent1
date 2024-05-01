package com.pezesha.agent.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.PezeshaAgentApp
import com.pezesha.agent.R
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentLoginBinding
import com.pezesha.agent.utils.GlobalMethods
import com.pezesha.agent.utils.extensions.init
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var globalMethods: GlobalMethods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (PezeshaAgentApp.sharedPrefsManager.appToken.isNotEmpty()) navigateToHomePage()
        initObservers()
        initUI()
        initListeners()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect {
                binding.btnSignIn.isEnabled = it
            }
        }

        viewModel.phoneError.observe(viewLifecycleOwner) {
            binding.tlPhone.error = it
        }

        viewModel.pinError.observe(viewLifecycleOwner) {
            binding.tlPin.error = it
        }

    }

    private fun initUI() {
        binding.apply {
            ccp.init()
            btnSignIn.setOnClickListener {
                sendLoginRequest()
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            etPhone.doAfterTextChanged { viewModel.setPhoneNumber(it.toString()) }
            etPin.doAfterTextChanged { viewModel.setPin(it.toString()) }
            ccp.setOnCountryChangeListener { viewModel.setCountryCode(ccp.selectedCountryCode) }
        }
    }

    private fun sendLoginRequest() {
        viewModel.login().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    PezeshaAgentApp.sharedPrefsManager.appToken = "YES"
                    navigateToHomePage()
                }
            }
        }
    }

    private fun navigateToHomePage() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        requireActivity().finish()
    }

}
