package com.example.reposearchapp.model.issue

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.reposearchapp.R

enum class IssueType(
    val state: String,
    @StringRes
    val optionName: Int,
    @DrawableRes
    val iconResId: Int
) {
    OPEN("open", R.string.name_open_option, R.drawable.ic_issue_open),
    CLOSED("closed", R.string.name_closed_option, R.drawable.ic_issue_closed),
    ALL("all", R.string.name_all_option, R.drawable.ic_issue_open);

    companion object {
        fun getIssueTypeByState(state: String?): IssueType =
            values().find { it.state == state } ?: ALL

        fun getIssueTypeByOrdinary(ordinary: Long): IssueType =
            values().find { it.ordinal == ordinary.toInt() } ?: ALL
    }
}