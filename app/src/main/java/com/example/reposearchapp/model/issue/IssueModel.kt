package com.example.reposearchapp.model.issue

import androidx.recyclerview.widget.DiffUtil

data class IssueModel(
    val id: Int,
    val createdAt: String,
    val repositoryTitle: String,
    val issueNumber: Int,
    val issueTitle: String,
    val state: IssueType
) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<IssueModel>() {
            override fun areItemsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean =
                oldItem == newItem
        }
    }
}
