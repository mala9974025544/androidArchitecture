package com.precticle.androidarchitecture.data.api

import com.precticle.androidarchitecture.data.model.User
import com.precticle.androidarchitecture.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiWithPaging{

    @GET("users")
     fun getUsers(@Query("page") page: Int): Call<UserResponse>

}