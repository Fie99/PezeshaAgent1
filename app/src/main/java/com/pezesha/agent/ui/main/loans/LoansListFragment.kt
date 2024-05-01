package com.pezesha.agent.ui.main.loans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pezesha.agent.databinding.FragmentLoansListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoansListFragment : Fragment() {
    private var _binding: FragmentLoansListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoansListBinding.inflate(inflater)
        return binding.root
    }

}
