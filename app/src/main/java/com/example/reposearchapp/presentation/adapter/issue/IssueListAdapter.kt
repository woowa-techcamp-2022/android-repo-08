package com.example.reposearchapp.presentation.adapter.issue

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.example.reposearchapp.databinding.ItemIssueBinding
import com.example.reposearchapp.model.issue.IssueModel
import com.example.reposearchapp.presentation.home.issue.IssueFragment

class IssueListAdapter : PagingDataAdapter<IssueModel, IssueViewHolder>(IssueModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            ItemIssueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }
}