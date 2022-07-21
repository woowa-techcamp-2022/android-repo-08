package com.example.reposearchapp.util

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("circleImageUrl")
fun ImageView.setCircleImageFromImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context)
            .load(imageUrl)
            .circleCrop()
            .into(this)
    }
}

@SuppressLint("UseCompatTextViewDrawableApis")
@BindingAdapter("tvDrawableTint")
fun TextView.setTextViewDrawableTint(color: String?) {
    val defaultColor = "#ffffff"
    val c = color ?: defaultColor

    val colorCode = try {
        Color.parseColor(c)
    } catch (e: IllegalArgumentException) {
        Color.parseColor(defaultColor)
    }

    this.compoundDrawableTintList = ColorStateList.valueOf(colorCode)
}