package com.example.reposearchapp.data.entity.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    val scope: String,
    @SerialName("token_type")
    val tokenType: String
)