package com.pezesha.agent.ui.main.addSme.kyc.kin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.FragmentNextOfKinDetailsBinding
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsFragment
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NextOfKinFragment : Fragment() {
    private var _binding: FragmentNextOfKinDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val kycDetailsViewModel: KycDetailsViewModel by activityViewModels()

    private val viewModel: NextOfKinViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextOfKinDetailsBinding.inflate(inflater)
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
            etRelationship.doAfterTextChanged { viewModel.setRelationShip(it.toString()) }
            etFirstName.doAfterTextChanged { viewModel.setFirstName(it.toString().trim()) }
            etSecondName.doAfterTextChanged { viewModel.setSecondName(it.toString().trim()) }
            etLastName.doAfterTextChanged { viewModel.setLastName(it.toString().trim()) }
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
                viewModel.isSubmitEnabled.collect {
                    btnNext.isEnabled = it
                }
            }

            viewModel.phoneError.observe(viewLifecycleOwner) {
                tlPhoneNumber.error = it
            }

            viewModel.firstNameError.observe(viewLifecycleOwner) {
                tlFirstName.error = it
            }

            viewModel.secondNameError.observe(viewLifecycleOwner) {
                tlSecondName.error = it
            }

            viewModel.lastNameError.observe(viewLifecycleOwner) {
                tlLastName.error = it
            }

        }

    }

    private fun initUI() {
        binding.apply {
            btnBack.setOnClickListener {
                kycDetailsViewModel.navigate(KycDetailsFragment.PERSONAL_FRAGMENT_INDEX)
            }

            btnNext.setOnClickListener {
                kycDetailsViewModel.navigate(KycDetailsFragment.BUSINESS_FRAGMENT_INDEX)
            }

        }

        initRelationshipTF()

    }

    private fun initRelationshipTF() {
        val list = resources.getStringArray(R.array.relationship).asList()
        val adapter =
            ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, list)
        binding.etRelationship.setAdapter(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
