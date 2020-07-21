package com.precticle.androidarchitecture.paytm.networking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppConfig (

    var id:Int = 0,

    @SerializedName("merchant_id")
    var merchantId: String? = null,

    @SerializedName("channel")
    var channel: String? = null,

    @SerializedName("industry_type")
    var industryType: String? = null,

    @SerializedName("website")
    var website: String? = null

):Parcelable
