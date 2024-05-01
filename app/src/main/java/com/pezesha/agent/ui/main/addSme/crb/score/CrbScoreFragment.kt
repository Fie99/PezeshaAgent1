package com.pezesha.agent.ui.main.addSme.crb.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.databinding.FragmentCrbScoreBinding
import com.pezesha.agent.ui.main.addSme.AddSmeViewModel
import com.pezesha.agent.utils.extensions.htmlText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CrbScoreFragment : Fragment() {

    private var _binding: FragmentCrbScoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CrbScoreViewModel by viewModels()

    private val addSmeViewModel: AddSmeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.retrieveReasons()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrbScoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.apply {
            btnProceed.setOnClickListener { navigateOut() }
            toolbar.setOnClickListener { navigateOut() }
        }
    }

    private fun navigateOut() {
        addSmeViewModel.moveToStage(AddSmeViewModel.DOCUMENTS_STAGE)
        findNavController().navigateUp()
    }

    private fun initObservers() {
        binding.apply {

            lifecycleScope.launch {
                viewModel.reasons.collect {
                    tvReasons.htmlText(it)
                }
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
