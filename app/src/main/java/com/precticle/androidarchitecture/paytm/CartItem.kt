package com.precticle.androidarchitecture.paytm

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CartItem  (
    var id: String = UUID.randomUUID().toString(),
    var product: Product? = null,
    var quantity: Int = 0
):Parcelable
