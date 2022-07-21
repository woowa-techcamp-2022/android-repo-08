package com.example.reposearchapp.data.repository.notification

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reposearchapp.data.Result
import com.example.reposearchapp.data.entity.notification.Notification
import com.example.reposearchapp.data.remote.GithubApi
import com.example.reposearchapp.data.safeApiCall
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class NotificationPagingSource(
    private val gitApiService: GithubApi,
    private val perPage: Int
) : PagingSource<Int, Notification>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val page = params.key ?: 1

        return when (val result = safeApiCall { gitApiService.getNotifications(page, perPage) }) {
            is Result.Error -> LoadResult.Error(Exception(result.exception))
            is Result.Success -> {
                // 각 알림별 댓글 개수 별도 요청
                coroutineScope {
                    result.data.map { noti ->
                        launch {
                            noti.subject.url?.let { url ->
                                noti.commentsNum = getSubject(url)
                            }
                        }
                    }.joinAll()
                }

                LoadResult.Page(
                    data = result.data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (result.data.isEmpty()) null else page + 1
                )
            }
        }
    }

    private suspend fun getSubject(subjectUrl: String): Int {
        return when (val result = safeApiCall { gitApiService.getSubjectByUrl(subjectUrl) }) {
            is Result.Error -> 0
            is Result.Success -> result.data.totalComment
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}