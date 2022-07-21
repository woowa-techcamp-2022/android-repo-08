package com.example.reposearchapp.presentation.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.reposearchapp.R
import com.example.reposearchapp.model.search.RepoItemModel
import com.example.reposearchapp.databinding.ItemRepoBinding

class RepoAdapter : PagingDataAdapter<RepoItemModel, RepoViewHolder>(RepoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = DataBindingUtil.inflate<ItemRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_repo,
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<RepoItemModel>() {
    override fun areItemsTheSame(
        oldItem: RepoItemModel, newItem: RepoItemModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RepoItemModel, newItem: RepoItemModel
    ): Boolean {
        return oldItem == newItem
    }
}
