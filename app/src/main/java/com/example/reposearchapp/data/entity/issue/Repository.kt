package com.example.reposearchapp.data.entity.issue

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    @SerialName("full_name")
    val fullName: String?
)