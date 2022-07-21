package com.example.reposearchapp.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("name")
    val name: String?,
    @SerialName("bio")
    val bio: String?,
    @SerialName("login")
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("blog")
    val blog: String,
    @SerialName("email")
    val email: String?,
    @SerialName("followers")
    val followers: Int,
    @SerialName("following")
    val following: Int,
    @SerialName("location")
    val location: String,
    @SerialName("repos_url")
    val reposUrl: String,
    @SerialName("public_repos")
    val publicRepoCount: Int,
    @SerialName("total_private_repos")
    val privateRepoCount: Int?,
    @SerialName("starred_url")
    val starredUrl: String
)