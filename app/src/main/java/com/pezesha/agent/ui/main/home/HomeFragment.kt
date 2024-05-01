package com.pezesha.agent.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.FragmentHomeBinding
import com.pezesha.agent.ui.main.customers.CustomersAdapter
import com.pezesha.agent.utils.extensions.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
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
            rvRecentBusinesses.adapter = adapter
            bsRegisteredBiz.setNumber(data?.filter { it.isRegistrationComplete == 1 }?.size.toString())
            bsUnregisteredBiz.setNumber(data?.filter { it.isRegistrationComplete == 0 }?.size.toString())
        }
    }

    private fun navigateToCustomer(customer: Customer) {
        binding.root.shortSnackbar("Customer selected is ${customer.customerName}")
    }

    private fun initUI() {
        binding.apply {
            btnFaq.setOnClickListener { it.shortSnackbar("Coming soon") }
            btnViewAll.setOnClickListener { it.shortSnackbar("Coming soon") }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
