package com.dev.batchfinal.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Context.dp2px(resource: Resources, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resource.displayMetrics
    ).toInt()
}

fun Context.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun String.removeZero(): String {
    val data = this
    return when {
        data.endsWith(".0") -> {
            data.substring(0, data.length - 2)
        }
        data.endsWith(".00") -> {
            data.substring(0, data.length - 3)
        }
        else -> {
            this
        }
    }
}

fun Double.roundToTwoDigits(): String {
    val df = DecimalFormat("#.##")
    val dfs = DecimalFormatSymbols()
    dfs.decimalSeparator = '.'
    df.decimalFormatSymbols = dfs
    return df.format(this).replace(",",".")
}


fun View.setBackgroundTint(@ColorRes colorResId: Int) {
    this.backgroundTintList = ContextCompat.getColorStateList(this.context, colorResId)
}