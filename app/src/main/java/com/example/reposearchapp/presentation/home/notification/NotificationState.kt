package com.example.reposearchapp.presentation.home.notification

import com.example.reposearchapp.model.notification.NotificationModel

sealed interface NotificationState {
    object UnInitialState : NotificationState
    object Loading : NotificationState

    object FetchFinish : NotificationState

    data class Error(
        val errorMsg: Int
    ) : NotificationState

    data class ErrorNotificationRead(
        val errorMsg: Int
    ) : NotificationState

    object SuccessRead : NotificationState
}