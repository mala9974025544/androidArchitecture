package com.precticle.androidarchitecture.data.api

import com.precticle.androidarchitecture.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi{

    @GET("users")
     suspend fun getUsers(): List<User>

}