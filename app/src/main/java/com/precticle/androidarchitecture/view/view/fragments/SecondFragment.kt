package com.precticle.androidarchitecture.view.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.precticle.androidarchitecture.R
import com.precticle.test.util.Utils

class SecondFragment : BaseFragment() {
    var str: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_second, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { str = it.getString("key") }
        Utils.showAlertDialogue(this.activity!!, str)

    }

    companion object {
        fun getInstance(str: String): SecondFragment {
            var bundle = Bundle()
            bundle.putString("key", str)
            var fragment = SecondFragment()
            fragment.arguments = bundle
            return fragment

        }
    }

}