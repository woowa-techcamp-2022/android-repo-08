package com.example.reposearchapp.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import com.example.reposearchapp.model.issue.IssueType
import com.example.reposearchapp.presentation.adapter.issue.IssueOptionAdapter

class FilterSpinner(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatSpinner(context, attributeSet) {

    private lateinit var onFocusedListener: (Boolean) -> Unit
    private var isOpened = false

    fun setOnFocusedListener(onFocusedListener: (Boolean) -> Unit) {
        this.onFocusedListener = onFocusedListener
    }

    /**
     * get focus
     */
    override fun performClick(): Boolean {
        if (::onFocusedListener.isInitialized) {
            isOpened = true
            onFocusedListener(isOpened)
        }
        return super.performClick()
    }

    /**
     * loss focus
     */
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (::onFocusedListener.isInitialized && isOpened && hasWindowFocus) {
            isOpened = false
            onFocusedListener(false)
        }
        super.onWindowFocusChanged(hasWindowFocus)
    }

    fun setOnItemClickListener(onItemClickListener: (Long) -> Unit) {
        // https://boheeee.tistory.com/11
        this.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemClickListener(id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}