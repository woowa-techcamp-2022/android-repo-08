package com.example.reposearchapp.data.entity.notification.read

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationReadResponse(
    @SerialName("status")
    val status: Int
)
