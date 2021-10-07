package com.vinted.demovinted.data.models.remote

import com.vinted.demovinted.data.models.Item
import kotlinx.serialization.Serializable

@Serializable
data class ItemsResponse(
    val items: List<ItemResponse>?
) {

    @Serializable
    data class ItemResponse(
        val id: Int,
        val price: Double,
        val photo: String,
        val brand: String,
        val category: String,
        val size: String? = null
    ) {

        fun toDomain() = Item(
            id = id,
            price = price,
            photo = photo,
            brand = brand,
            category = category,
            size = size
        )
    }
}