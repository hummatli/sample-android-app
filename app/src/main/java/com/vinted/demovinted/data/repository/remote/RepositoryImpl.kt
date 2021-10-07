package com.vinted.demovinted.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vinted.demovinted.data.models.Item
import com.vinted.demovinted.data.repository.remote.api.Api
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) : Repository {

    override fun getSearchResults(query: String): Flow<PagingData<Item>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ItemPagingSource(api, query) }
        ).flow

    companion object {

        const val PAGE_SIZE = 20
    }
}



