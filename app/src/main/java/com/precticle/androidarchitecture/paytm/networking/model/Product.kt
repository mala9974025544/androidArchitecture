package com.precticle.androidarchitecture.paytm.networking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: Long = 0,
    var name: String? = null,
    @SerializedName("image")
    var imageUrl: String? = null,
    var price: Float = 0f
):Parcelable

