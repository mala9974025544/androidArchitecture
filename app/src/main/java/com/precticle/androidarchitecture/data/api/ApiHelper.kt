package com.precticle.androidarchitecture.data.api

import com.precticle.androidarchitecture.paytm.PrepareOrderRequest

class ApiHelper(private val apiService: NetworkApi) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun prepareOrder() = apiService.prepareOrder(request = PrepareOrderRequest())
}