package com.pezesha.agent.ui.dialogs.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.pezesha.agent.R
import com.pezesha.agent.databinding.DateDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class DatePopUpDialog : DialogFragment() {

    private var _binding: DateDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var popupDialogListener: DateListener
    private var constraints = "TODAY"

    private lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ThemeOverlay_App_MaterialAlertDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DateDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myCalendar = Calendar.getInstance()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            btnPick.setOnClickListener { pickDate() }
            btnCancel.setOnClickListener { dialog?.dismiss() }

            if (constraints == "ABOVE18") {
                myCalendar.add(Calendar.YEAR, -18)
                binding.calendar.init(
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH),
                    null
                )
            }
        }
    }

    private fun pickDate() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        binding.calendar.apply {
            val dayOfMonth = dayOfMonth
            val monthOfYear = month
            val year = year

            myCalendar[year, monthOfYear] = dayOfMonth
            val date = sdf.format(myCalendar.time)
            if (constraints == "TODAY") {
                if (checkDate(myCalendar.timeInMillis)) {
                    Toast.makeText(
                        requireContext(),
                        "Date cannot be later than today",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }

            if (constraints == "ABOVE18") {
                if (isUserAtLeast18(myCalendar.timeInMillis)) {
                    Toast.makeText(
                        requireContext(),
                        "This Application is Open to only users with at least 18 years of age.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }

            popupDialogListener.onDateSelected(myCalendar, date)
            dialog?.dismiss()
        }

    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun setDateDialogListener(popupDialogListener: DateListener) {
        this.popupDialogListener = popupDialogListener
    }

    fun setDateConstraints(constraints: String) {
        this.constraints = constraints
    }

    private fun checkDate(date: Long): Boolean {
        val timestampNow = Calendar.getInstance().timeInMillis
        return date > timestampNow
    }

    private fun isUserAtLeast18(date: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        val minDate = calendar.timeInMillis
        return date >= minDate
    }

}
