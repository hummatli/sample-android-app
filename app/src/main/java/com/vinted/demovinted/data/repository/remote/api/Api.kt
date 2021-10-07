package com.vinted.demovinted.data.repository.remote.api

import com.vinted.demovinted.data.models.remote.ItemsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/items")
    suspend fun getItemsFeed(
        @Query("page") page: Int,
        @Query("search_text") searchText: String): ItemsResponse
}
