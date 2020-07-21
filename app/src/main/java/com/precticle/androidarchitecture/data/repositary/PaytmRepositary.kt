package com.precticle.androidarchitecture.data.repositary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.precticle.androidarchitecture.data.api.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaytmRepositary(val apiHelper: ApiHelper) {


     fun prepareOrder(request: PrepareOrderRequest) : LiveData<PrepareOrderResponse>? {
          val data: MutableLiveData<PrepareOrderResponse> = MutableLiveData<PrepareOrderResponse>()
        apiHelper.prepareOrder(request)
            ?.enqueue(object : Callback<PrepareOrderResponse?> {
                override fun onResponse(
                    call: Call<PrepareOrderResponse?>?,
                    response: Response<PrepareOrderResponse?>
                ) {

                    if (response.body() != null) {
                       return   data.setValue(response.body())

                    }
                }

                override fun onFailure(call: Call<PrepareOrderResponse?>?,t: Throwable?) {
                     return data.setValue(null)
                }
            })
        return null
    }


}