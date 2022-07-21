package com.example.reposearchapp.presentation.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.withStyledAttributes
import com.example.reposearchapp.R

class CustomSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SearchView(context, attrs, defStyleAttr) {
    init {
        val queryTextView =
            findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)

        var bgDrawable: Drawable? = null
        var bgFocusedDrawable: Drawable? = null
        context.withStyledAttributes(attrs, R.styleable.CustomSearchView) {
            bgDrawable = getDrawable(R.styleable.CustomSearchView_android_background)
            bgFocusedDrawable = getDrawable(R.styleable.CustomSearchView_focusedBackground)
        }

        queryTextView.setOnFocusChangeListener { _, isFocus ->
            val drawable = if (isFocus) {
                bgFocusedDrawable
            } else {
                bgDrawable
            }
            drawable?.let { background = it }
        }
    }
}