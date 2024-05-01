package com.pezesha.agent.ui.registersme.applyLoan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.FragmentLoanApplicationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LoanApplicationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoanApplicationFragment : Fragment() {
    private var _binding: FragmentLoanApplicationBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoanApplicationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            btnSubmit.setOnClickListener {
                navController.navigate(R.id.action_loanApplicationFragment_to_loanApplicationSubmittedFragment)
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoanApplicationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LoanApplicationFragment().apply {

        }
    }
}