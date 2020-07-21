package com.precticle.androidarchitecture.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.precticle.androidarchitecture.data.api.ApiHelper
import com.precticle.androidarchitecture.data.repositary.PaytmRepositary


class PaytmViewModel(var paytmRepositary: PaytmRepositary) :ViewModel(){
    /*fun prepareOrder(requests: PrepareOrderRequest)= liveData(Dispatchers.IO) {
        emit(com.precticle.androidarchitecture.utils.Resource.loading(data = null))
       try{
           emit(com.precticle.androidarchitecture.utils.Resource.success(data = paytmRepositary.prepareOrder(requests)))
       }
       catch (exception:Exception){
           emit(com.precticle.androidarchitecture.utils.Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
       }*/

    private var articleResponseLiveData: LiveData<PrepareOrderResponse>? = null

     fun prepareOrder(requests: PrepareOrderRequest): LiveData<PrepareOrderResponse>? {
         articleResponseLiveData= paytmRepositary.prepareOrder(requests)
        return articleResponseLiveData
    }


    class ViewModelFactory(private val apiHelper: ApiHelper):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PaytmViewModel::class.java)) {
                return PaytmViewModel(PaytmRepositary(apiHelper = apiHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }


    }

}