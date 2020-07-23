package com.precticle.androidarchitecture.data.api

import com.precticle.androidarchitecture.paytm.networking.model.PrepareOrderRequest

class ApiHelper(private val apiService: NetworkApi) {

    suspend fun getUsers() = apiService.getUsers()

     fun prepareOrder(request: PrepareOrderRequest) = apiService.prepareOrder(request = PrepareOrderRequest())
}