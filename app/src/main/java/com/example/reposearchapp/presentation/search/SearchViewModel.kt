package com.example.reposearchapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reposearchapp.data.repository.search.SearchRepository
import com.example.reposearchapp.model.search.RepoItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    val pagingDataFlow: Flow<PagingData<RepoItemModel>>
    val uiAction: (UiAction) -> Unit

    init {
        viewModelScope.launch {
            searchRepository.parsingColor()
        }
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .debounce(400L)
            .onStart { emit(UiAction.Search(query = "")) }

        pagingDataFlow = searches
            .flatMapLatest {
                if (it.query.isBlank())
                    flowOf(PagingData.empty())
                else searchRepo(query = it.query)
            }
            .cachedIn(viewModelScope)

        uiAction = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }
    }

    private fun searchRepo(query: String): Flow<PagingData<RepoItemModel>> =
        searchRepository.search(query)

}

sealed class UiAction {
    data class Search(val query: String) : UiAction()
}