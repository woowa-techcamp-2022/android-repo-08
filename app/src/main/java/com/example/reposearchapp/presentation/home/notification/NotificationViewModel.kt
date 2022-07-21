package com.example.reposearchapp.presentation.home.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.reposearchapp.R
import com.example.reposearchapp.data.repository.notification.NotificationRepository
import com.example.reposearchapp.model.notification.NotificationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notificationStateLiveData =
        MutableLiveData<NotificationState>(NotificationState.UnInitialState)
    val notificationStateLiveData: LiveData<NotificationState>
        get() = _notificationStateLiveData

    lateinit var notificationDataFlow: Flow<PagingData<NotificationModel>>
        private set


    fun getNotifications() = viewModelScope.launch {
        notificationDataFlow = notificationRepository.getNotifications().cachedIn(viewModelScope)
            .map { notifications -> notifications.map { notification -> notification.toModel() } }
            .catch {
                _notificationStateLiveData.value =
                    NotificationState.Error(R.string.error_issue_list)
            }
        _notificationStateLiveData.value = NotificationState.FetchFinish
    }

    fun readNotification(notificationModel: NotificationModel) = viewModelScope.launch {
        try {
            val threadId = notificationModel.threadId
            notificationRepository.readNotificationByThreadId(threadId)
            _notificationStateLiveData.value = NotificationState.SuccessRead
        } catch (exception: Exception) {
            _notificationStateLiveData.value =
                NotificationState.ErrorNotificationRead(R.string.error_notification_read)
        }
    }
}