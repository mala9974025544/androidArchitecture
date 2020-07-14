package com.precticle.androidarchitecture.data.api

class ApiHelper(private val apiService: NetworkApi) {

    suspend fun getUsers() = apiService.getUsers()
}