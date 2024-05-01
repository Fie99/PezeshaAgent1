package com.pezesha.agent.ui.main.customers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pezesha.agent.R
import com.pezesha.agent.data.local.dummyData.CUSTOMER_LIST
import com.pezesha.agent.data.local.dummyData.LOAN_LIST
import com.pezesha.agent.databinding.FragmentListOfLoansBinding


class ListOfLoansFragment : Fragment() {
    private var _binding:FragmentListOfLoansBinding?=null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListOfLoansBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            rvLoanList.layoutManager = LinearLayoutManager(requireContext())
            rvLoanList.adapter = ListOfLoansAdapter(LOAN_LIST)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListOfLoansFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ListOfLoansFragment().apply {
            }
    }
}