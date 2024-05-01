package com.pezesha.agent.utils.extensions

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.pezesha.agent.R


fun TextInputLayout.loadingState() {
    val context = this.context
    val loadingStates by lazy {
        ColorStateList(
            arrayOf(intArrayOf()),
            intArrayOf(context.fetchPrimaryColor())
        )
    }

    this.setEndIconTintList(loadingStates)
    this.endIconMode = TextInputLayout.END_ICON_CUSTOM
    this.endIconDrawable = context.getProgressBarDrawable()
    this.error = null
}

fun TextInputLayout.successState() {
    val context = this.context
    val successStates by lazy {
        ContextCompat.getColorStateList(
            context,
            android.R.color.black
        )
    }

    this.setEndIconTintList(successStates)
    this.endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
    this.error = null
}

fun TextInputLayout.errorState() {
    val context = this.context
    val errorStates by lazy {
        ContextCompat.getColorStateList(
            context,
            android.R.color.holo_red_dark
        )
    }

    this.setEndIconTintList(errorStates)
    this.endIconMode = TextInputLayout.END_ICON_CUSTOM
    this.endIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_error)
    this.error = "Could not load data"

}