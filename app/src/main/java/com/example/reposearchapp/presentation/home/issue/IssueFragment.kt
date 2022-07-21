package com.example.reposearchapp.presentation.home.issue

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentIssueBinding
import com.example.reposearchapp.model.issue.IssueType
import com.example.reposearchapp.presentation.adapter.issue.IssueListAdapter
import com.example.reposearchapp.presentation.adapter.issue.IssueOptionAdapter
import com.example.reposearchapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IssueFragment : BaseFragment<FragmentIssueBinding>(R.layout.fragment_issue) {

    // TODO 의존성 주입으로 수정할 예정
    private val viewModel: IssueViewModel by viewModels()

    private lateinit var issueListAdapter: IssueListAdapter
    private lateinit var issueOptionListAdapter: IssueOptionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindViews()
        observeIssue()
    }

    private fun initViews() = with(binding) {
        // 이슈 리스트 초기화
        issueListAdapter = IssueListAdapter()
        issueRecyclerView.adapter = issueListAdapter

        // 필터를 위한 옵션 리스트 초기화
        issueOptionListAdapter =
            IssueOptionAdapter(requireContext(), IssueType.values().toList(), viewModel)
        spinnerIssueOption.adapter = issueOptionListAdapter
    }

    private fun bindViews() = with(binding) {
        spinnerIssueOption.setOnItemClickListener { viewModel.changeIssueType(it) }
        spinnerIssueOption.setOnFocusedListener { hasFocused ->
            layoutIssueFilter.setBackgroundResource(
                if (hasFocused) R.drawable.background_issue_filter_focused
                else R.drawable.background_issue_filter_unfocused
            )
        }

        swipeRefreshLayout.setOnRefreshListener {
            issueListAdapter.refresh()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeIssue() {
        viewModel.issueStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                IssueState.UnInitialState -> {}
                IssueState.Loading -> handleLoading()
                IssueState.FetchFinish -> {}
                is IssueState.Error -> handleError(it)
            }
        }
        // paging data
        lifecycleScope.launch {
            viewModel.getIssues().collectLatest {
                issueListAdapter.submitData(it)
            }
        }
    }

    private fun handleLoading() {

    }

    private fun handleSuccess() {
    }

    private fun handleError(state: IssueState.Error) {
    }

    companion object {
        const val TAG = "IssueFragment"
        fun newInstance() = IssueFragment()
    }
}