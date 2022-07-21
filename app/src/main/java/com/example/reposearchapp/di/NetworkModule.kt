package com.example.reposearchapp.di

import com.example.reposearchapp.data.remote.AccessApi
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.data.remote.GithubServiceInterceptor
import com.example.reposearchapp.data.repository.login.AccessTokenRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Qualifier


@Module
@InstallIn(SingletonComponent::class)
object ConverterModule {

    @Provides
    fun provideJsonConverter(
    ): Converter.Factory {
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(MediaType.parse("application/json")!!)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    fun provideInterceptor(
        accessTokenRepository: AccessTokenRepository
    ): GithubServiceInterceptor {
        return GithubServiceInterceptor(accessTokenRepository)
    }
}

@Qualifier
annotation class AccessApiOkHttpClient

@Qualifier
annotation class GithubApiOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    @AccessApiOkHttpClient
    @Provides
    fun provideAccessApiOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @GithubApiOkHttpClient
    @Provides
    fun provideGithubApiOkHttpClient(
        interceptor: GithubServiceInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAccessApi(
        @AccessApiOkHttpClient
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): AccessApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converter)
            .baseUrl("https://github.com/")
            .build()
            .create(AccessApi::class.java)
    }

    @Provides
    fun provideGithubApi(
        @GithubApiOkHttpClient
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): GithubApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converter)
            .baseUrl("https://api.github.com")
            .build()
            .create(GithubApi::class.java)
    }
}