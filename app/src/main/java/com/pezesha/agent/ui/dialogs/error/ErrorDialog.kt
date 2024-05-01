package com.pezesha.agent.ui.dialogs.error

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.pezesha.agent.R

class ErrorDialog(context: Context) {

    private var popupDialogListener: (() -> Unit)? = null
    private var message = ""

    private val dialogView by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_error, null)
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
        initUI()
        return dialog
    }

    private fun initUI() {
        dialogView.findViewById<TextView>(R.id.tv_msg).text = message
        dialogView.findViewById<Button>(R.id.btn_error).setOnClickListener {
            dialog.dismiss()
            popupDialogListener?.invoke()
        }

    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun setPopupDialogListener(popupDialogListener: (() -> Unit)?) {
        this.popupDialogListener = popupDialogListener
    }

    fun setOnClickListener(listener: () -> Unit) {
        listener() // or you could use optional if the lister is nullable "listener?.invoke()"
    }
}