package com.example.reposearchapp.di

import android.content.Context
import com.example.reposearchapp.data.remote.AccessApi
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.data.repository.ProfileRepository
import com.example.reposearchapp.data.repository.issue.IssueRepository
import com.example.reposearchapp.data.repository.login.AccessTokenRepository
import com.example.reposearchapp.data.repository.notification.NotificationRepository
import com.example.reposearchapp.data.repository.search.SearchRepository
import com.example.reposearchapp.util.GithubLanguageColorUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAccessTokenRepository(
        @ApplicationContext application: Context,
        accessApi: AccessApi
    ) = AccessTokenRepository(application, accessApi)

    @Singleton
    @Provides
    fun provideIssueRepository(
        githubApi: GithubApi
    ) = IssueRepository(githubApi)

    @Singleton
    @Provides
    fun provideNotificationRepository(
        githubApi: GithubApi
    ) = NotificationRepository(githubApi)

    @Singleton
    @Provides
    fun provideSearchRepository(
        githubLanguageColorUtil: GithubLanguageColorUtil,
        githubApi: GithubApi
    ) = SearchRepository(githubLanguageColorUtil, githubApi)

    @Singleton
    @Provides
    fun provideProfileRepository(
        githubApi: GithubApi
    ) = ProfileRepository(githubApi)
}

@Module
@InstallIn(SingletonComponent::class)
object GithubLanguageColorUtilModule {

    @Singleton
    @Provides
    fun provideGithubLanguageColorUtil(
        @ApplicationContext application: Context,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): GithubLanguageColorUtil =
        GithubLanguageColorUtil(
            application,
            defaultDispatcher
        )
}