package com.pezesha.agent.ui.dialogs.date

import androidx.fragment.app.FragmentActivity
import java.util.*

class DateDialog {

    fun showDateDialog(
        constraints: String = "TODAY",
        activity: FragmentActivity,
        dialogListener: DateListener
    ) {
        val dialog = DatePopUpDialog()
        dialog.setDateConstraints(constraints)
        dialog.setDateDialogListener(dialogListener)
        dialog.show(activity.supportFragmentManager, "Date")
    }
}

interface DateListener {

    fun onDateSelected(calendar: Calendar, date: String)
}