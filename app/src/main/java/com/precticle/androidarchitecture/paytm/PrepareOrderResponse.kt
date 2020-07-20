package com.precticle.androidarchitecture.paytm

import com.google.gson.annotations.SerializedName

class PrepareOrderResponse {
    var id: Long = 0

    @SerializedName("order_gateway_id")
    var orderGatewayId: String? = null

    @SerializedName("amount")
    var amount: String? = null

    @SerializedName("order_items")
    var orderItems: List<OrderItem>? = null
    var status: String? = null
}
