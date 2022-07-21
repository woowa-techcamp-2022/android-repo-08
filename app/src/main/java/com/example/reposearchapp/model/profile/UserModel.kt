package com.example.reposearchapp.model.profile

data class UserModel(
    val name: String?,
    val bio: String?,
    val login: String,
    val avatarUrl: String,
    val blog: String,
    val email: String?,
    val followers: Int,
    val following: Int,
    val location: String,
    val reposCount: Int,
    val starredCount: Int
)