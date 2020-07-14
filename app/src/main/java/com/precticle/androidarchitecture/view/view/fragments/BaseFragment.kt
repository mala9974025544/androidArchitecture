package com.precticle.androidarchitecture.view.view.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.precticle.test.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), CoroutineScope {
    private lateinit var mJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mJob

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mJob = Job()
        Utils.hideKeyboard(requireActivity())
    }

    override fun onDestroyView() {
        mJob.cancel()
        super.onDestroyView()
    }
}