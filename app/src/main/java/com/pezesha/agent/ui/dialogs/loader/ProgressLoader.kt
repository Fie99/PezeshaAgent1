package com.pezesha.agent.ui.dialogs.loader

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.pezesha.agent.R

class ProgressLoader(context: Context) {

    private val dialogView by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
    }

    private val builder by lazy {
        AlertDialog.Builder(context).setView(dialogView)
    }

    private var cancelable = false
    private var isBackGroundTransparent: Boolean = true

    private lateinit var dialog: AlertDialog

    //  dialog create
    fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

}