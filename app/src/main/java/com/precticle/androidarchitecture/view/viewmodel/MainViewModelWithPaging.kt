package com.precticle.androidarchitecture.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.precticle.androidarchitecture.data.model.User
import com.precticle.androidarchitecture.data.repositary.MainRepository
import com.precticle.androidarchitecture.utils.Resource
import com.precticle.androidarchitecture.utils.UserDataSource
import kotlinx.coroutines.Dispatchers

class MainViewModelWithPaging() : ViewModel() {

    var userPagedList: LiveData<PagedList<User>>
    private var liveDataSource: LiveData<UserDataSource>
    init {
        val itemDataSourceFactory = UserDataSource.UserDataSourceFactory()
        liveDataSource = itemDataSourceFactory.userLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(UserDataSource.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}