package com.vinted.demovinted.data.models

import android.os.Parcelable
import com.vinted.demovinted.BuildConfig
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
data class Item(
    val id: Int,
    val price: Double,
    val photo: String,
    val brand: String,
    val category: String,
    val size: String? = null
): Parcelable {
    val fullPhotoURL: String
        get() = "${BuildConfig.BASE_API_URL}/images/$photo"

    val priceFormatted: String
        get() = DecimalFormat("#,###.00").format(price)
}
