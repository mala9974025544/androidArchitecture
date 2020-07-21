package com.precticle.androidarchitecture.data.repositary

import com.precticle.androidarchitecture.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()


}