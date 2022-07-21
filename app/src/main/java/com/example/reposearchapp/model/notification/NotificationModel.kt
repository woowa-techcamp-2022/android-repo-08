package com.example.reposearchapp.model.notification

import androidx.recyclerview.widget.DiffUtil
import com.example.reposearchapp.model.issue.IssueModel

data class NotificationModel(
    val id: String,
    val reason: NotificationReasonType,
    val repositoryTitle: String,
    val title: String,
    val type: String,
    val number: String,
    val threadId: Long,
    val avatarUrl: String?,
    val updatedAt: String,
    val commentsNum: Int
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<NotificationModel>() {
            override fun areItemsTheSame(
                oldItem: NotificationModel,
                newItem: NotificationModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NotificationModel,
                newItem: NotificationModel
            ): Boolean =
                oldItem == newItem
        }
    }
}
