package com.precticle.androidarchitecture.data.api

class ApiHelper(private val apiService: NetworkApi) {

    suspend fun getUsers() = apiService.getUsers()

     fun prepareOrder(request: PrepareOrderRequest) = apiService.prepareOrder(request = PrepareOrderRequest())
}