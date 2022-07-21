package com.example.reposearchapp.data.remote

import com.example.reposearchapp.data.entity.search.RepoResponse
import com.example.reposearchapp.data.entity.StarredByUserResponse
import com.example.reposearchapp.data.entity.UserResponse
import com.example.reposearchapp.data.entity.issue.Issue
import com.example.reposearchapp.data.entity.notification.Notification
import com.example.reposearchapp.data.entity.notification.read.NotificationReadResponse
import com.example.reposearchapp.data.entity.subject.Subject
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {
    /**
     * state 에 맞는 이슈 정보들 요청
     */
    @GET("/issues")
    suspend fun getIssues(
        @Query("state") state: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
    ): Response<List<Issue>>

    /**
     * 알림 데이터 전체 요청
     */
    @GET("/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<Notification>>

    /**
     * 알림 읽음 처리 요청
     */
    @PATCH("/notifications/threads/{thread_id}")
    suspend fun readNotification(@Path("thread_id") threadId: Long): Response<NotificationReadResponse>

    /**
     * url 을 이용해 특정 subject(issue, PR 등등) 정보 요청
     */
    @GET
    suspend fun getSubjectByUrl(@Url subjectUrl: String): Response<Subject>

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<RepoResponse>

    @Headers("Accept: application/json")
    @GET("user")
    suspend fun getUser(): Response<UserResponse>

    @GET("/user/starred")
    suspend fun getStarsByUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): List<StarredByUserResponse>
}