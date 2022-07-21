package com.example.reposearchapp.presentation.home.issue

import com.example.reposearchapp.model.issue.IssueModel

sealed interface IssueState {
    object UnInitialState : IssueState
    object Loading : IssueState
    object FetchFinish: IssueState

    data class Error(
        val errorMsg: Int
    ) : IssueState
}