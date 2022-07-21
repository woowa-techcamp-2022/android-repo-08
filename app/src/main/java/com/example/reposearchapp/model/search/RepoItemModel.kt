package com.example.reposearchapp.model.search

import kotlinx.serialization.Serializable

@Serializable
data class RepoItemModel(
    val description: String?,
    val id: Int,
    val language: String?,
    val languageColor: String?,
    val name: String,
    val ownerName: String,
    val ownerImgUrl: String,
    val stargazersCount: Int
)