package com.example.reposearchapp.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun showSnackBar(view: View, msg: String) {
    Snackbar.make(view, msg, Toast.LENGTH_SHORT).show()
}