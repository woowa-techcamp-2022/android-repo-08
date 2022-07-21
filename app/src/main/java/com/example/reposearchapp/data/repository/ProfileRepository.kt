package com.example.reposearchapp.data.repository

import com.example.reposearchapp.data.Result
import com.example.reposearchapp.data.entity.ProfileEntity
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.data.safeApiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val githubApi: GithubApi
) {
    /**
     * 로그인한 사용자의 정보 요청
     */
    suspend fun getUser(): ProfileEntity {
        return when (val result = safeApiCall { githubApi.getUser() }) {
            is Result.Success -> {
                val profileEntity: ProfileEntity
                coroutineScope {
                    var totalStarCount = 0
                    var page = 0
                    while (true) {
                        val star = async { githubApi.getStarsByUser(page) }
                        page++
                        try {
                            val starCnt = star.await().size
                            totalStarCount += starCnt
                            if (starCnt == 0) break
                        } catch (e: Exception) {
                            break
                        }
                    }

                    profileEntity = ProfileEntity(
                        userResponse = result.data,
                        starredByUserResponse = totalStarCount
                    )
                }
                profileEntity
            }
            is Result.Error -> {
                throw Exception(result.exception)
            }
        }
    }
}