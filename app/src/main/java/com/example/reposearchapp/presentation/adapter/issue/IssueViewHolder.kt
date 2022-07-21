package com.example.reposearchapp.presentation.adapter.issue

import androidx.recyclerview.widget.RecyclerView
import com.example.reposearchapp.databinding.ItemIssueBinding
import com.example.reposearchapp.model.issue.IssueModel

class IssueViewHolder(
    private val binding: ItemIssueBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(issue: IssueModel) = with(binding) {
        // TODO data binding 으로
        tvRepoName.text = "${issue.repositoryTitle} #${issue.issueNumber}"
        tvIssueTitle.text = issue.issueTitle
        tvIssueDate.text = issue.createdAt

        imgIssueState.setImageResource(issue.state.iconResId)
    }
}