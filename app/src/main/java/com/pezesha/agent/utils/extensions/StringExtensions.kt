package com.pezesha.agent.utils.extensions

import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun String.displayDate(): String {
    return try {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val date = originalFormat.parse(this)
        targetFormat.format(date!!)
    } catch (e: Exception) {
        this
    }

}

fun String.notificationDate(): String {
    return try {
        val originalFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val date = originalFormat.parse(this)
        targetFormat.format(date!!)
    } catch (e: Exception) {
        this
    }

}

fun String.formatPhoneNumber(countryCode: String): String {

    return when {
        (this.startsWith("+") && this.length == 13 && countryCode == this.substring(1, 4)) -> this
        (this.startsWith("0", true) && this.length == 10) -> "+$countryCode${this.substring(1)}"
        (this.length == 9) -> "+$countryCode$this"
        (this.length == 12 && countryCode == this.substring(0, 3)) -> "+$this"
        else -> this
    }

}


fun Double.displayCash(): String {
    return try {
        val df = DecimalFormat("#,###.##")
        df.roundingMode = RoundingMode.DOWN
        df.format(this)
    } catch (e: Exception) {
        this.toString()
    }

}
