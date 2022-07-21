package com.example.reposearchapp.data.repository.login

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.reposearchapp.BuildConfig
import com.example.reposearchapp.data.Result
import com.example.reposearchapp.data.entity.login.AccessTokenRequest
import com.example.reposearchapp.data.remote.AccessApi
import com.example.reposearchapp.data.safeApiCall
import com.example.reposearchapp.util.Event
import javax.inject.Inject

class AccessTokenRepository @Inject constructor(
    private val application: Context,
    private val accessApi: AccessApi
) {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private val sharedPrefsFile: String = ACCESS_TOKEN_KEY
    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        sharedPrefsFile,
        mainKeyAlias,
        application,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var accessToken: String? = null
        private set

    suspend fun requestToken(code: String): Event {
        val result = safeApiCall {
            accessApi.requestAccessToken(
                AccessTokenRequest(
                    clientId = BuildConfig.GITHUB_CLIENT_ID,
                    clientSecret = BuildConfig.GITHUB_CLIENT_SECRET,
                    code = code
                )
            )
        }

        return when (result) {
            is Result.Success -> {
                accessToken = result.data.accessToken
                saveToken(result.data.accessToken)
                Event.Success(result.data.accessToken)
            }
            is Result.Error -> {
                Event.Error(result.exception)
            }
        }
    }

    fun requestToken() {
        val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, "") ?: ""
        accessToken = token
    }

    private fun saveToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(ACCESS_TOKEN_KEY, token)
            apply()
        }
    }

    fun removeToken() {
        with(sharedPreferences.edit()) {
            remove(ACCESS_TOKEN_KEY)
            apply()
        }
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}