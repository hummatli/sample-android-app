package com.vinted.demovinted.data.repository.remote

import androidx.paging.PagingData
import com.vinted.demovinted.data.models.Item
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getSearchResults(query: String): Flow<PagingData<Item>>
}
