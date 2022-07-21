package com.example.reposearchapp.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

// cache
fun ImageView.clear() = Glide.with(context).clear(this)

fun ImageView.load(
    url: String,
    corner: Float = 0f,
    scaleType: Transformation<Bitmap> = CenterCrop()
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (corner > 0) transform(scaleType, RoundedCorners(corner.fromDpToPx()))
        }
        .into(this)
}