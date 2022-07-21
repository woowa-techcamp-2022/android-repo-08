package com.example.reposearchapp.data.entity

import com.example.reposearchapp.model.profile.UserModel

data class ProfileEntity(
    val userResponse: UserResponse,
    val starredByUserResponse: Int
) {
    fun toModel() = UserModel(
        name = userResponse.name,
        bio = userResponse.bio,
        login = userResponse.login,
        avatarUrl = userResponse.avatarUrl,
        blog = userResponse.blog,
        email = userResponse.email,
        followers = userResponse.followers,
        following = userResponse.following,
        location = userResponse.location,
        reposCount = userResponse.publicRepoCount + (userResponse.privateRepoCount ?: 0),
        starredCount = starredByUserResponse
    )
}