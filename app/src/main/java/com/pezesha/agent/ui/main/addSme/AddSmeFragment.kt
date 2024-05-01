package com.pezesha.agent.ui.main.addSme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.FragmentAddSmeBinding
import com.pezesha.agent.ui.main.addSme.AddSmeButtonStates.ACTIVE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddSmeFragment : Fragment() {

    private var _binding: FragmentAddSmeBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val addSmeViewModel: AddSmeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSmeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initObservers()
    }

    private fun initObservers() {
        binding.apply {
            lifecycleScope.launch {
                addSmeViewModel.consentState.collect {
                    addConsent.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.getConsentFragment)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                addSmeViewModel.kycState.collect {
                    addKyc.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.action_addSmeFragment_to_kycDetailsFragment)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                addSmeViewModel.crbState.collect {
                    addCrb.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.crbCheckFragment)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                addSmeViewModel.docsState.collect {
                    addDocuments.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.action_addSmeFragment_to_documentsActivity)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                addSmeViewModel.quizState.collect {
                    addQuestions.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.action_addSmeFragment_to_psychometricFragment)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                addSmeViewModel.applyState.collect {
                    addLoan.apply {
                        changeState(it.toString())
                        isEnabled = it == ACTIVE
                        setOnClickListener {
                            navigateTo(R.id.action_addSmeFragment_to_applyLoanActivity)
                            addSmeViewModel.moveToStage(1)
                        }
                    }
                }
            }

        }

    }

    private fun navigateTo(destinationID: Int) {
        navController.navigate(destinationID)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}

enum class AddSmeButtonStates {
    ACTIVE {
        override fun toString() = "ACTIVE"
    },
    INACTIVE {
        override fun toString() = "INACTIVE"
    },
    DONE {
        override fun toString() = "DONE"
    }
}
