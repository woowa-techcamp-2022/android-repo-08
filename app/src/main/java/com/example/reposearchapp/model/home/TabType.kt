package com.example.reposearchapp.model.home

enum class TabType(
    val position: Int,
    val title: String
) {
    ISSUE(0, "Issue"),
    NOTIFICATION(1, "Notification");

    companion object {
        fun getTabTypeByPosition(position: Int): TabType? =
            values().find { it.position == position }
    }
}