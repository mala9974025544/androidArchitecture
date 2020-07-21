package com.precticle.androidarchitecture.utils

import android.app.Service
import android.content.ClipData.Item
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.precticle.androidarchitecture.data.api.ApiHelper
import com.precticle.androidarchitecture.data.api.ServiceClass
import com.precticle.androidarchitecture.data.api.ServiceClassWithPaging
import com.precticle.androidarchitecture.data.model.User
import com.precticle.androidarchitecture.data.model.UserResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Morris on 03,June,2019
 */
class UserDataSource : PageKeyedDataSource<Int, User?>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User?>
    ) {
        //val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = ServiceClassWithPaging.apiService.getUsers(FIRST_PAGE)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    apiResponse.let {
                        callback.onResult(apiResponse.users!!, null, FIRST_PAGE + 1)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }
        })
    }



    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int?, User?>) {
        val call = ServiceClassWithPaging.apiService.getUsers(params.key)

        call.enqueue(object :  Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!

                    val key = if (params.key > 1) params.key - 1 else 0
                    apiResponse.let {
                        callback.onResult(apiResponse.users!!, key)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int?, User?>) {
        val call = ServiceClassWithPaging.apiService.getUsers(params.key)

        call.enqueue(object :  Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!

                    val key = params.key + 1
                    apiResponse.let {
                        callback.onResult(apiResponse.users!!, key)
                    }
                }
            }
            override fun onFailure(call: Call<UserResponse>,t: Throwable) {
            }
        })
    }
    //this will be called once to load the initial data
    companion object {
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1
    }
    class UserDataSourceFactory : DataSource.Factory<Int, User>() {
        val userLiveDataSource = MutableLiveData<UserDataSource>()
        override fun create(): UserDataSource {
            val userDataSource = UserDataSource()
            userLiveDataSource.postValue(userDataSource)
            return userDataSource
        }
    }
}