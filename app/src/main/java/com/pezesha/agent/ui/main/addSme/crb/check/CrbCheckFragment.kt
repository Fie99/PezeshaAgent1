package com.pezesha.agent.ui.main.addSme.crb.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.FragmentCrbCheckBinding
import com.pezesha.agent.utils.extensions.showBalloon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CrbCheckFragment : Fragment() {

    private var _binding: FragmentCrbCheckBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val viewModel: CrbCheckViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrbCheckBinding.inflate(inflater)
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
                    btnSend.isEnabled = it
                }
            }

            lifecycleScope.launch {
                viewModel.isSubmitEnabled.collect {
                    buttonProceed.isEnabled = it
                }
            }

            viewModel.phoneError.observe(viewLifecycleOwner) {
                tlPhoneNumber.error = it
            }

        }

    }

    private fun initUI() {
        binding.apply {
            buttonProceed.setOnClickListener {
                navController.navigate(R.id.action_crbCheckFragment_to_crbScoreFragment)
            }

            btnSend.setOnClickListener {
                viewModel.performCrbCheck()
                binding.tlPhoneNumber.showBalloon("SMS Sent to Customer.")
            }

            toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}