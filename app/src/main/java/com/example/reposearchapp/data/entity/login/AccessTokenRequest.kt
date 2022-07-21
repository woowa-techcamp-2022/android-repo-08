package com.example.reposearchapp.data.entity.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    @SerialName("client_id")
    val clientId : String,
    @SerialName("client_secret")
    val clientSecret: String,
    val code: String
)