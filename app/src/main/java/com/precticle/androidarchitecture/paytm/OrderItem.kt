package com.precticle.androidarchitecture.paytm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItem (
    @SerializedName("product_id")
    var productId: Long = 0,
    var quantity: Int = 0,
    var product: Product? = null
):Parcelable
