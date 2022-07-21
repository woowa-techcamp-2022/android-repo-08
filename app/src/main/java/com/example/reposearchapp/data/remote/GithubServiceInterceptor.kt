package com.example.reposearchapp.data.remote

import com.example.reposearchapp.data.repository.login.AccessTokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GithubServiceInterceptor @Inject constructor(
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        accessTokenRepository.requestToken()
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer ${accessTokenRepository.accessToken}")
            .build()
        return chain.proceed(request)
    }
}