package com.pezesha.agent.ui.dialogs.county

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pezesha.agent.R
import com.pezesha.agent.data.models.County
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.databinding.DialogCountyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountyDialog : DialogFragment() {

    private var _binding: DialogCountyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CountyViewModel by viewModels()

    private lateinit var adapter: CountyAdapter
    var listener: CountyChosenListener? = null

    interface CountyChosenListener {
        fun onChosenCounty(county: County)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ThemeOverlay_App_MaterialAlertDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogCountyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
    }

    private fun initUI() {
        binding.apply {
            ivClose.setOnClickListener { dialog?.dismiss() }
            adapter = CountyAdapter {
                listener?.onChosenCounty(it)
                dialog?.dismiss()
            }

            rvCounties.adapter = adapter
            rvCounties.itemAnimator = DefaultItemAnimator()
        }
    }

    private fun initData() {
        viewModel.getCountiesList().observe(viewLifecycleOwner) { resource ->
            resource?.let {
                when (it) {
                    is Resource.Error -> error()
                    is Resource.Loading -> {
                        binding.emptyDataParent.root.visibility = View.GONE
                        binding.pbLoader.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        hideLoader()
                        adapter.submitList(it.data?.counties?.sortedBy { item ->
                            item.name
                        })
                    }
                }
            }
        }
    }

    private fun hideLoader() {
        binding.pbLoader.visibility = View.GONE
    }

    private fun error() {
        hideLoader()
        binding.emptyDataParent.apply {
            tvTitle.text = getString(R.string.no_counties_found)
            root.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
