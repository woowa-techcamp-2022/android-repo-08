package com.example.reposearchapp.presentation.adapter.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.example.reposearchapp.databinding.ItemNotificationBinding
import com.example.reposearchapp.model.notification.NotificationModel

class NotificationAdapter :
    PagingDataAdapter<NotificationModel, NotificationViewHolder>(NotificationModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }

    fun getItemByPosition(position: Int) = getItem(position)
}