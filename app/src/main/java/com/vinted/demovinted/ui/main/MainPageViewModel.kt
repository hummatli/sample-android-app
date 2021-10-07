package com.vinted.demovinted.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vinted.demovinted.base.BaseViewModel
import com.vinted.demovinted.data.repository.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val items = currentQuery.switchMap {
        repository.getSearchResults(it)
            .asLiveData(viewModelScope.coroutineContext)
            .cachedIn(viewModelScope)
    }

    fun searchItems(query: String) {
        if(currentQuery.value != query) {
            currentQuery.value = query
        }
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}