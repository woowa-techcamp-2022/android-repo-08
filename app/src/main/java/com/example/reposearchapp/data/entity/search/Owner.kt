package com.example.reposearchapp.data.entity.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String,
    val login: String
)