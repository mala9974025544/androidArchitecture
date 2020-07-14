package com.precticle.androidarchitecture.view.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.view.adapter.MainAdapterWIthPageList
import com.precticle.androidarchitecture.view.viewmodel.MainViewModelWithPaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityWithPaging : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_paging)
        val adapter = MainAdapterWIthPageList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemViewModel  = ViewModelProviders.of(this)
            .get(MainViewModelWithPaging::class.java)

        itemViewModel.userPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        recyclerView.adapter = adapter
    }

}

