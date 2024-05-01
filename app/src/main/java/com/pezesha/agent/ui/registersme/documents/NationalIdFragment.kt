package com.pezesha.agent.ui.registersme.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pezesha.agent.R


/**
 * A simple [Fragment] subclass.
 * Use the [NationalIdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NationalIdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_national_id, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NationalIdFragment.
         */
        @JvmStatic
        fun newInstance() = NationalIdFragment().apply {

        }
    }
}