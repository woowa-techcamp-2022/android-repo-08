package com.example.reposearchapp.data.entity.issue

import com.example.reposearchapp.model.issue.IssueModel
import com.example.reposearchapp.model.issue.IssueType
import com.example.reposearchapp.util.getDiffFromNow
import com.example.reposearchapp.util.toDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Issue(
    @SerialName("id")
    val id: Int?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("repository")
    val repository: Repository,
    @SerialName("number")
    val number: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("state")
    val state: String?
) {
    fun toModel() = IssueModel(
        id = id ?: 0,
        createdAt = createdAt?.toDate()?.getDiffFromNow() ?: "None",
        repositoryTitle = repository.fullName ?: "UnKnown",
        issueNumber = number ?: 0,
        issueTitle = title ?: "UnKnown",
        state = IssueType.getIssueTypeByState(state)
    )
}