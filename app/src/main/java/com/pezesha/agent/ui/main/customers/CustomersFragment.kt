package com.pezesha.agent.ui.main.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentCustomersBinding
import com.pezesha.agent.utils.extensions.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomersFragment : Fragment() {
    private var _binding: FragmentCustomersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomersFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initUI()
    }

    private fun initObservers() {
        viewModel.getCustomersList().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    parseData(resource.data)
                }
            }
        }
    }

    private fun parseData(data: List<Customer>?) {
        binding.apply {
            val adapter = CustomersAdapter { navigateToCustomer(it) }
            adapter.submitList(data)
            rvCustomerList.adapter = adapter
        }
    }

    private fun navigateToCustomer(customer: Customer) {
        binding.root.shortSnackbar("Customer selected is ${customer.customerName}")
    }

    private fun initUI() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}