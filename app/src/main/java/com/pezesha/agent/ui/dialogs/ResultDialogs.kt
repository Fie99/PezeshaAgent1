package com.pezesha.agent.ui.dialogs

import android.content.Context
import com.pezesha.agent.ui.dialogs.error.ErrorDialog
import com.pezesha.agent.ui.dialogs.success.SuccessDialog

object ResultDialogs {

    fun showErrorMessage(
        context: Context,
        message: String,
        dialogListener: (() -> Unit)?
    ) {
        val dialog = ErrorDialog(context)
        dialog.setPopupDialogListener(dialogListener)
        dialog.setMessage(message)
        val alertDialog = dialog.create()
        alertDialog.show()
    }

    fun showSuccessMessage(
        context: Context,
        message: String,
        dialogListener: (() -> Unit)?
    ) {
        val dialog = SuccessDialog(context)
        dialog.setPopupDialogListener(dialogListener)
        dialog.setMessage(message)
        val alertDialog = dialog.create()
        alertDialog.show()
    }

}