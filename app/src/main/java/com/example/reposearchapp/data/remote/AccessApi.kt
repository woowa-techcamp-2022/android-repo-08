package com.example.reposearchapp.data.remote

import com.example.reposearchapp.data.entity.login.AccessTokenRequest
import com.example.reposearchapp.data.entity.login.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AccessApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun requestAccessToken(
        @Body tokenRequest: AccessTokenRequest
    ): Response<AccessTokenResponse>
}