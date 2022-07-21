package com.example.reposearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reposearchapp.data.entity.search.Item
import com.example.reposearchapp.data.remote.GithubApi

class SearchPagingSource(
    private val query: String,
    private val githubApi: GithubApi
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val start = params.key ?: STARTING_KEY

        when (val result = safeApiCall { githubApi.getRepos(query, start) }) {
            is Result.Success -> {
                return LoadResult.Page(
                    data = result.data.items,
                    prevKey = when (start) {
                        STARTING_KEY -> null
                        else -> start - 1
                    },
                    nextKey = if (result.data.items.isEmpty()) null
                    else start + 1
                )
            }

            is Result.Error -> {
                return LoadResult.Error(Exception(result.exception))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val STARTING_KEY = 1
    }
}