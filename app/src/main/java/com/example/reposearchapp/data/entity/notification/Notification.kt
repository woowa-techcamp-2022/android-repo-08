package com.example.reposearchapp.data.entity.notification

import com.example.reposearchapp.model.notification.NotificationModel
import com.example.reposearchapp.model.notification.NotificationReasonType
import com.example.reposearchapp.util.getDiffFromNow
import com.example.reposearchapp.util.toDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    @SerialName("id")
    val id: String?,
    @SerialName("reason")
    val reason: String?,
    @SerialName("repository")
    val repository: Repository,
    @SerialName("subject")
    val subject: Subject,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("url")
    val url: String?,
    var commentsNum: Int = 0,
) {
    fun toModel() = NotificationModel(
        id = id ?: "none",
        reason = NotificationReasonType.getReasonType(reason),
        repositoryTitle = repository.fullName ?: "UnKnown",
        title = subject.title ?: "UnKnown",
        type = subject.type ?: "none",
        number = subject.url?.substringAfterLast("/") ?: "0",
        threadId = url?.substringAfterLast("/")?.toLong() ?: 0,
        avatarUrl = repository.owner?.avatarUrl,
        updatedAt = updatedAt?.toDate()?.getDiffFromNow() ?: "0",
        commentsNum = commentsNum
    )
}