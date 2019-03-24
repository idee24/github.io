package com.bookitng.bookitng.extensions


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import com.bookitng.bookitng.R

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun errorMessage(context: Context, layout: ConstraintLayout, message: String) {
    val snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
    val textView = snackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error_outline_black_24dp, 0, 0, 0)
    textView.compoundDrawablePadding = 5
    snackbar.show()
}
