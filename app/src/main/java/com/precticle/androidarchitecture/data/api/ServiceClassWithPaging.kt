package com.precticle.androidarchitecture.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceClassWithPaging {

    private const val BASE_URL = "https://reqres.in/api/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: NetworkApiWithPaging = getRetrofit().create(NetworkApiWithPaging::class.java)


}