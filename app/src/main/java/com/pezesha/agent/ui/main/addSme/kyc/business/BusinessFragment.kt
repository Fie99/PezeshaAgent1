package com.pezesha.agent.ui.main.addSme.kyc.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pezesha.agent.R
import com.pezesha.agent.data.models.BizSector
import com.pezesha.agent.data.models.County
import com.pezesha.agent.data.models.SubCounty
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentBusinessDetailsBinding
import com.pezesha.agent.ui.dialogs.county.CountyDialog
import com.pezesha.agent.ui.dialogs.date.DateDialog
import com.pezesha.agent.ui.dialogs.date.DateListener
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsFragment.Companion.KIN_FRAGMENT_INDEX
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsFragment.Companion.NAVIGATE_OUT_FRAGMENT_INDEX
import com.pezesha.agent.ui.main.addSme.kyc.KycDetailsViewModel
import com.pezesha.agent.utils.extensions.errorState
import com.pezesha.agent.utils.extensions.loadingState
import com.pezesha.agent.utils.extensions.successState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


@AndroidEntryPoint
class BusinessFragment : Fragment() {
    private var _binding: FragmentBusinessDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusinessViewModel by viewModels()

    private val kycViewModel: KycDetailsViewModel by activityViewModels()

    private val businessDetailsList = arrayListOf<BizSector>()
    private val countiesList = arrayListOf<County>()
    private val subCountiesList = arrayListOf<SubCounty>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        initUI()
    }

    private fun initObservers() {
        binding.apply {
            lifecycleScope.launch {
                viewModel.isSubmitEnabled.collect {
                    btnNext.isEnabled = it
                }
            }

            viewModel.bizNameError.observe(viewLifecycleOwner) {
                tlBusinessName.error = it
            }

            viewModel.numOfEmployeesError.observe(viewLifecycleOwner) {
                tlNumberOfStaff.error = it
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            etBusinessName.doAfterTextChanged { viewModel.setBizName(it.toString()) }
            etNumberOfStaff.doAfterTextChanged { viewModel.setNumOfEmployees(it.toString()) }
            etDate.doAfterTextChanged { viewModel.setBizStartDate(it.toString()) }
            etBusinessSector.doAfterTextChanged { viewModel.setBizSector(it.toString()) }
            etCounty.doAfterTextChanged { viewModel.setBizCounty(it.toString()) }
            etSubCounty.doAfterTextChanged { viewModel.setBizSubCounty(it.toString()) }
            etBizIndustry.doAfterTextChanged { viewModel.setBizType(it.toString())}
        }
    }

    private fun initUI() {
        binding.apply {
            btnBack.setOnClickListener { kycViewModel.navigate(KIN_FRAGMENT_INDEX) }
            btnNext.setOnClickListener { sendRequest() }

            etDate.setOnClickListener { showDatePicker() }
            tlBusinessDate.setEndIconOnClickListener { etDate.performClick() }

        }

        initBizSectorTextField()
        initBizCountyTextField()
        initBizTypeTextField()
    }

    private fun initBizTypeTextField() {
        val bizTypes = resources.getStringArray(R.array.biz_types).asList()
        val adapter =
            ArrayAdapter(requireContext(), R.layout.list_item_dropdown_menu, bizTypes)
        binding.etBizIndustry.setAdapter(adapter)
    }

    private fun showDatePicker() {
        DateDialog().showDateDialog(
            activity = requireActivity(),
            dialogListener = object : DateListener {
                override fun onDateSelected(calendar: Calendar, date: String) {
                    binding.etDate.setText(date)
                }
            })
    }

    private fun initBizSectorTextField() {
        viewModel.getBusinessIndustries()
        viewModel.businessIndustries.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        businessDetailsList.clear()
                        businessDetailsList.addAll(it.data?.bizSectors ?: listOf())
                        binding.tlBusinessSector.successState()
                        binding.etBusinessSector.setOnClickListener { showBizDialog() }
                        binding.tlBusinessSector.setEndIconOnClickListener { showBizDialog() }
                    }

                    is Resource.Error -> {
                        binding.tlBusinessSector.errorState()
                        binding.tlBusinessSector.setOnClickListener { viewModel.getBusinessIndustries() }
                        binding.tlBusinessSector.setEndIconOnClickListener { viewModel.getBusinessIndustries() }
                    }

                    is Resource.Loading -> binding.tlBusinessSector.loadingState()
                }
            }
        }

    }

    private fun initBizCountyTextField() {
        viewModel.getCountiesList()
        viewModel.counties.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        countiesList.clear()
                        countiesList.addAll(it.data?.counties ?: listOf())
                        binding.tlCounty.successState()
                        binding.etCounty.setOnClickListener { showCountyDialog() }
                        binding.tlCounty.setEndIconOnClickListener { showCountyDialog() }
                    }

                    is Resource.Error -> {
                        binding.tlCounty.errorState()
                        binding.etCounty.setOnClickListener { viewModel.getCountiesList() }
                        binding.tlCounty.setEndIconOnClickListener { viewModel.getCountiesList() }
                    }

                    is Resource.Loading -> binding.tlCounty.loadingState()
                }
            }
        }
    }

    private fun initBizSubCountyTextField() {
        viewModel.getSubCountiesList() //chosenCounty.code
        viewModel.subCounties.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        subCountiesList.clear()
                        subCountiesList.addAll(it.data?.subCounties ?: listOf())
                        binding.tlBusinessSubCounty.successState()
                        binding.etSubCounty.setOnClickListener { showSubCountyDialog() }
                        binding.tlBusinessSubCounty.setEndIconOnClickListener { showSubCountyDialog() }
                    }

                    is Resource.Error -> {
                        binding.tlBusinessSubCounty.errorState()
                        binding.etSubCounty.setOnClickListener {
                            viewModel.getSubCountiesList(
//                                chosenCounty.code
                            )
                        }
                        binding.tlBusinessSubCounty.setEndIconOnClickListener {
                            viewModel.getSubCountiesList(

                            ) //chosenCounty.code
                        }
                    }

                    is Resource.Loading -> binding.tlBusinessSubCounty.loadingState()
                }
            }
        }
        //
    }

    private fun showCountyDialog() {
        val listener = object : CountyDialog.CountyChosenListener {
            override fun onChosenCounty(county: County) {
                binding.etCounty.setText(county.name)
                initBizSubCountyTextField()
            }
        }
        val ft: FragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment = CountyDialog()
        newFragment.listener = listener
        newFragment.show(ft, "dialog")
    }

    private fun showSubCountyDialog() {
        val items: Array<String> = subCountiesList.map { it.name }.toTypedArray()

        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(getString(R.string.sub_counties))
            .setItems(items) { dialog, which ->
                val lw: ListView = (dialog as AlertDialog).listView
                val checkedItem: String = lw.adapter.getItem(which) as String
                binding.etSubCounty.setText(checkedItem)
            }
            .show()
    }

    private fun showBizDialog() {
        val items: Array<String> = businessDetailsList.map { it.name }.toTypedArray()

        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(getString(R.string.business_industries))
            .setItems(items) { dialog, which ->
                val lw: ListView = (dialog as AlertDialog).listView
                val checkedItem: String = lw.adapter.getItem(which) as String
                binding.etBusinessSector.setText(checkedItem)
            }
            .show()
    }

    private fun sendRequest() {

        kycViewModel.navigate(NAVIGATE_OUT_FRAGMENT_INDEX)
    }


}