package com.sriyank.javatokotlindemo.extensions

import android.support.design.widget.TextInputLayout
import android.widget.EditText

fun EditText.isValid(layout: TextInputLayout): Boolean {

    return if (text.toString().trim().isEmpty()) {
        layout.error = "Cannot Be blank"
        false
    }
    else {
        layout.isErrorEnabled = false
        true
    }
}
