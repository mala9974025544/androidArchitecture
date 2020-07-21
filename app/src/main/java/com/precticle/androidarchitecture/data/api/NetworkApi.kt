package com.precticle.androidarchitecture.data.api

import com.precticle.androidarchitecture.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi{

    @GET("users")
     suspend fun getUsers(): List<User>

    @POST("prepareOrder")
     fun prepareOrder(@Body request: PrepareOrderRequest?): Call<PrepareOrderResponse>

}