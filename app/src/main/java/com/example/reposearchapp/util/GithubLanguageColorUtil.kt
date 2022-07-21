package com.example.reposearchapp.util

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.IOException


class GithubLanguageColorUtil(
    private val applicationContext: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    var colorMap: Map<String, String>? = null
        private set

    suspend fun parseJson() {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "githubLanguageColor.json")

        jsonFileString?.let {
            withContext(defaultDispatcher) {
                colorMap = Json.parseToJsonElement(it).jsonObject.toMap()
                    .mapValues {
                        it.value.toString().replace(
                            "\"",
                            ""
                        )
                    }
            }
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}