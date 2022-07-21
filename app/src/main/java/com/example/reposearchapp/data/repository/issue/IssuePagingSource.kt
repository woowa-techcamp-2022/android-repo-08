package com.example.reposearchapp.data.repository.issue

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reposearchapp.data.Result
import com.example.reposearchapp.data.entity.issue.Issue
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.data.safeApiCall

class IssuePagingSource(
    private val githubApi: GithubApi,
    private val state: String,
    private val perPage: Int,
) : PagingSource<Int, Issue>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Issue> {
        val page = params.key ?: 1

        return when (val result =
            safeApiCall {
                githubApi.getIssues(
                    state = state,
                    page = page,
                    perPage = perPage
                )
            }) {
            is Result.Error -> LoadResult.Error(Exception(result.exception))
            is Result.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (result.data.isEmpty()) null else page + 1
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Issue>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}