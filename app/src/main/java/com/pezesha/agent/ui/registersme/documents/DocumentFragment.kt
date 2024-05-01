package com.pezesha.agent.ui.registersme.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pezesha.agent.R
import com.pezesha.agent.databinding.FragmentDocumentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DocumentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DocumentFragment : Fragment() {
    private var _binding: FragmentDocumentBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDocumentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            btnNationalId.setOnClickListener {
                navController.navigate(R.id.action_documentFragment_to_nationalIdFragment)
            }
            buttonSubmit.setOnClickListener {
                navController.navigate(R.id.action_documentFragment_to_documentScoringFragment)
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
         * @return A new instance of fragment DocumentFragment.
         */
        @JvmStatic
        fun newInstance() = DocumentFragment().apply {}

    }
}