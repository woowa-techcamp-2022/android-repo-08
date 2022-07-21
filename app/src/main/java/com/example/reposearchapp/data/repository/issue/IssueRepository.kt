package com.example.reposearchapp.data.repository.issue

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.reposearchapp.data.entity.issue.Issue
import com.example.reposearchapp.data.remote.GithubApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IssueRepository @Inject constructor(
    private val githubApi: GithubApi
) {

    /**
     * state 에 따른 issues 요청
     * state : open, closed, all
     */
    fun getIssuesByPaging(state: String): Flow<PagingData<Issue>> {
        return Pager(
            config = PagingConfig(pageSize = PER_PAGE),
            pagingSourceFactory = { IssuePagingSource(githubApi, state, PER_PAGE) }
        ).flow
    }

    companion object {
        const val PER_PAGE = 20
    }
}