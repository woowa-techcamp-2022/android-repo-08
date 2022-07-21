package com.example.reposearchapp.data.entity.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?
)