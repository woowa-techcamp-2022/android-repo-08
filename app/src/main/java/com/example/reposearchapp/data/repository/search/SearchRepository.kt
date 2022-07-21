package com.example.reposearchapp.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.reposearchapp.model.search.RepoItemModel
import com.example.reposearchapp.data.SearchPagingSource
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.util.GithubLanguageColorUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 30

class SearchRepository @Inject constructor(
    private val color: GithubLanguageColorUtil,
    private val githubApi: GithubApi
) {
    suspend fun parsingColor() {
        color.parseJson()
    }

    fun search(query: String): Flow<PagingData<RepoItemModel>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(query, githubApi) }
        )
            .flow.map {
                it.map { repo ->
                    repo.toModel(color.colorMap?.get(repo.language))
                }
            }
}