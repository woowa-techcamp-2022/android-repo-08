package com.example.reposearchapp.presentation.adapter.notification

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reposearchapp.databinding.ItemNotificationBinding
import com.example.reposearchapp.model.notification.NotificationModel
import com.example.reposearchapp.util.load

class NotificationViewHolder(
    private val binding: ItemNotificationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(notification: NotificationModel) = with(binding) {
        tvRepoName.text = "${notification.repositoryTitle} #${notification.number}"
        tvNotificationDate.text = notification.updatedAt
        tvNotificationTitle.text = notification.title
        tvCommentsNumber.text = "${notification.commentsNum}"
        notification.avatarUrl?.let { imgRepositoryOwner.load(notification.avatarUrl, 14f) }
    }
}