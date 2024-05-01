package com.pezesha.agent.ui.main.addSme.kyc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pezesha.agent.databinding.FragmentKycDetailsBinding
import com.pezesha.agent.ui.main.addSme.AddSmeViewModel
import com.pezesha.agent.ui.main.addSme.AddSmeViewModel.Companion.CRB_STAGE
import com.pezesha.agent.ui.main.addSme.kyc.business.BusinessFragment
import com.pezesha.agent.ui.main.addSme.kyc.kin.NextOfKinFragment
import com.pezesha.agent.ui.main.addSme.kyc.personal.PersonalFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KycDetailsFragment : Fragment() {

    private var _binding: FragmentKycDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KycDetailsViewModel by activityViewModels()
    private val addSmeViewModel: AddSmeViewModel by activityViewModels()

    private lateinit var fragmentsAdapter: KycDetailsFragmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKycDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsAdapter =
            KycDetailsFragmentsAdapter(requireActivity().supportFragmentManager, lifecycle)
        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.apply {
            pager.apply {
                adapter = fragmentsAdapter
                isUserInputEnabled = false
                isSaveEnabled = true
            }

            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        }

    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.profileStage.collect {
                navigate(it)
            }
        }

        lifecycleScope.launch {
            viewModel.txtTitle.collect {
                binding.tvTitle.text = getString(it)
            }
        }

        lifecycleScope.launch {
            viewModel.businessStage.collect {
                binding.apply {
                    ivBusiness.setImageResource(it.imgRes)

                    tvBusiness.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            it.txtColor
                        )
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.personalStage.collect {
                binding.apply {
                    ivPersonal.setImageResource(it.imgRes)
                    tvPersonal.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            it.txtColor
                        )
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.kinStage.collect {
                binding.apply {
                    ivKin.setImageResource(it.imgRes)
                    tvKin.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            it.txtColor
                        )
                    )
                }
            }
        }

    }

    private fun navigate(position: Int) {
        when (position) {
            in 0..2 -> {
                viewModel.navigate(position)
                binding.pager.currentItem = position
            }

            NAVIGATE_OUT_FRAGMENT_INDEX -> {
                addSmeViewModel.moveToStage(CRB_STAGE)
                findNavController().navigateUp()
            }

            else -> findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val PERSONAL_FRAGMENT_INDEX = 0
        const val KIN_FRAGMENT_INDEX = 1
        const val BUSINESS_FRAGMENT_INDEX = 2
        const val NAVIGATE_OUT_FRAGMENT_INDEX = 999
    }

}

class KycDetailsFragmentsAdapter(
    fragment: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PersonalFragment()
            1 -> NextOfKinFragment()
            2 -> BusinessFragment()
            else -> PersonalFragment()
        }
    }
}
