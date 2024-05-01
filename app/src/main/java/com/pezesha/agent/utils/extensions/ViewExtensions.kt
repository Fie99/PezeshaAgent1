package com.pezesha.agent.utils.extensions

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDrawableOrThrow
import com.google.android.material.snackbar.Snackbar
import com.hbb20.CountryCodePicker
import com.pezesha.agent.R
import com.pezesha.agent.utils.Constants.SUPPORTED_COUNTRIES
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec

fun View.shortSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.longSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.shortAnchoredSnackbar(message: String, view: View) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setAnchorView(view)
        .show()
}

fun View.longAnchoredSnackbar(message: String, view: View) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAnchorView(view)
        .show()
}

fun Context.getProgressBarDrawable(): Drawable {
    val value = TypedValue()
    theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
    val progressBarStyle = value.data
    val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
    val array = obtainStyledAttributes(progressBarStyle, attributes)
    val drawable = array.getDrawableOrThrow(0)
    array.recycle()
    (drawable as? Animatable)?.start()
    return drawable
}

fun Context.fetchPrimaryColor(): Int {
    val array = obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
    val color = array.getColorOrThrow(0)
    array.recycle()
    return color
}

fun TextView.htmlText(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    else
        this.text = Html.fromHtml(text)
}

fun CountryCodePicker.init() {
    this.apply {
        val countriesSize = SUPPORTED_COUNTRIES.split(",").size.toDouble()
        val ht: Double = (countriesSize / 10 + 0.1).coerceAtMost(0.9)
        setCustomMasterCountries(SUPPORTED_COUNTRIES)
        setDialogEventsListener(object : CountryCodePicker.DialogEventsListener {
            override fun onCcpDialogOpen(dialog: Dialog?) {
                val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
                val height = (resources.displayMetrics.heightPixels * ht).toInt()
                dialog?.window?.setLayout(width, height)
            }

            override fun onCcpDialogDismiss(dialogInterface: DialogInterface?) {}

            override fun onCcpDialogCancel(dialogInterface: DialogInterface?) {}
        })
    }
}

fun View.showBalloon(msg: String) {
    val balloon = Balloon.Builder(this.context)
        .setWidthRatio(1.0f)
        .setHeight(BalloonSizeSpec.WRAP)
        .setText(msg)
        .setAutoDismissDuration(3000L)
        .setTextColorResource(android.R.color.black)
        .setTextSize(15f)
        .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        .setArrowSize(20)
        .setArrowPosition(0.5f)
        .setPadding(12)
        .setCornerRadius(10f)
        .setBackgroundColorResource(R.color.primary_color_medium)
        .setBalloonAnimation(BalloonAnimation.ELASTIC)
        .build()

    balloon.showAlignTop(this)

}
