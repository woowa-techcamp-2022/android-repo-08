package com.example.reposearchapp.presentation.search


import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.reposearchapp.R
import com.example.reposearchapp.databinding.FragmentSearchBinding
import com.example.reposearchapp.presentation.adapter.search.RepoAdapter
import com.example.reposearchapp.presentation.base.BaseFragment
import com.example.reposearchapp.util.KeyboardUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var adapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = searchViewModel

        initSearchView()
        initRecyclerView()
    }

    private fun initSearchView() {
        binding.searchView.doOnLayout {
            it.requestFocus()
            KeyboardUtil.show(it.findFocus())
        }

        binding.searchView.run {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchViewModel.uiAction(UiAction.Search(query = newText ?: ""))

                    return true
                }
            })
        }

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initRecyclerView() {
        adapter = RepoAdapter()
        binding.rvRepo.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.pagingDataFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
                    binding.groupSearchInfo.isVisible = isListEmpty
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(LAST_QUERY, binding.searchView.query.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val query = savedInstanceState?.getString(LAST_QUERY)
        binding.searchView.setQuery(query, false)
    }

    companion object {
        const val TAG = "SearchFragment"
        const val LAST_QUERY = "LAST_QUERY"
    }
}