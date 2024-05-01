package com.pezesha.agent.ui.main.addSme.kyc.personal

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
import com.pezesha.agent.databinding.FragmentPersonalDetailsBinding
import com.pezesha.agent.ui.dialogs.date.DateDialog
import com.pezesha.agent.ui.dialogs.date.DateListener
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsFragment.Companion.BUSINESS_FRAGMENT_INDEX
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsFragment.Companion.KIN_FRAGMENT_INDEX
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class PersonalFragment : Fragment() {
    private var _binding: FragmentPersonalDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: PersonalViewModel by viewModels()

    private val kycDetailsViewModel: KycDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalDetailsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initListeners()
        initObservers()
        initUI()
    }

    private fun initObservers() {
        binding.apply {

            viewModel.firstNameError.observe(viewLifecycleOwner) {
                tlFirstName.error = it
            }

            viewModel.secondNameError.observe(viewLifecycleOwner) {
                tlSecondName.error = it
            }

            viewModel.lastNameError.observe(viewLifecycleOwner) {
                tlLastName.error = it
            }

//            viewModel.emailError.observe(viewLifecycleOwner) {
//                tlEmail.error = it
//            }

            viewModel.nationalIdError.observe(viewLifecycleOwner) {
                tlNationalId.error = it
            }

            lifecycleScope.launch {
                viewModel.isSubmitEnabled.collect {
                    btnNext.isEnabled = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            etFirstName.doAfterTextChanged { viewModel.setFirstName(it.toString().trim()) }
            etSecondName.doAfterTextChanged { viewModel.setSecondName(it.toString().trim()) }
            etLastName.doAfterTextChanged { viewModel.setLastName(it.toString().trim()) }
//            etEmail.doAfterTextChanged { viewModel.setEmail(it.toString()) }
            etNationalId.doAfterTextChanged { viewModel.setNationalId(it.toString()) }
//            etGender.doAfterTextChanged { viewModel.setGender(it.toString()) }
            etDateOfBirth.doAfterTextChanged { viewModel.setDob(it.toString()) }
            etSocialMediaAccount.doAfterTextChanged { viewModel.setSocialMediaAccount(it.toString()) }
            etFacebook.doAfterTextChanged { viewModel.setFacebookProfile(it.toString()) }
            etTwitter.doAfterTextChanged { viewModel.setTwitterHandle(it.toString()) }
            etInstagram.doAfterTextChanged { viewModel.setInstagramUsername(it.toString()) }
        }

    }


    private fun initUI() {
        binding.apply {
            btnNext.setOnClickListener {
                kycDetailsViewModel.navigate(KIN_FRAGMENT_INDEX)
            }

            tlDateOfBirth.setEndIconOnClickListener { initDatePicker() }
            etDateOfBirth.setOnClickListener { initDatePicker() }
        }
        initSocialMediaTF()
    }

    private fun initSocialMediaTF() {
        val list = resources.getStringArray(R.array.biz_social_media).asList()
        val adapter =
            ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, list)
        binding.etSocialMediaAccount.setAdapter(adapter)
    }

    private fun initDatePicker() {
        DateDialog().showDateDialog(
            constraints = "ABOVE18",
            activity = requireActivity(),
            dialogListener = object : DateListener {
                override fun onDateSelected(calendar: Calendar, date: String) {
                    binding.etDateOfBirth.setText(date)
                }
            })
    }

}