package com.example.reposearchapp.data.entity.search

import com.example.reposearchapp.model.search.RepoItemModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val description: String?,
    val id: Int,
    val language: String?,
    val name: String,
    val owner: Owner,
    @SerialName("stargazers_count")
    val stargazersCount: Int
) {
    fun toModel(color: String?): RepoItemModel =
        RepoItemModel(
            description = description,
            id = id,
            language = language,
            languageColor = color,
            name = name,
            ownerName = owner.login,
            ownerImgUrl = owner.avatarUrl,
            stargazersCount = stargazersCount
        )
}