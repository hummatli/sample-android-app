package com.vinted.demovinted.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinted.demovinted.data.models.Item
import com.vinted.demovinted.data.repository.remote.api.Api
import retrofit2.HttpException
import java.io.IOException

private const val ITEM_STARTING_PAGE_INDEX = 0

class ItemPagingSource(
    private val api: Api,
    private val query: String
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val position = params.key ?: ITEM_STARTING_PAGE_INDEX

        return try {
            //Pass params.loadSize if you api accepts pageSize. Now we don't have
            val response = api.getItemsFeed(
                searchText = query,
                page = position
            )
            val items = response.items?.map { it.toDomain() } ?: listOf()

            LoadResult.Page(
                data = items,
                prevKey = if (position == ITEM_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (items.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}