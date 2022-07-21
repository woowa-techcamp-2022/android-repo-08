package com.example.reposearchapp.util

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
    return format.parse(this)
}
