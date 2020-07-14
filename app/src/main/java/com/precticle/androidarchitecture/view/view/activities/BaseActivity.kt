package com.precticle.androidarchitecture.view.view.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity() : AppCompatActivity(), CoroutineScope{
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +  mJob

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        mJob = Job()
    }
    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()

    }
    fun replaceFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(container, fragment, fragment.javaClass.simpleName).commit()
    }

    fun addFragment(container: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(container, fragment).commit()
    }

    fun addFragmentWithBackStack(containerId: Int, fragment: Fragment, animateBottomTop: Boolean = false, animateRightLeft: Boolean = false) {
        if (animateBottomTop) {
            fragment.exitTransition = Slide(Gravity.TOP)//.setDuration(400)
            fragment.enterTransition = Slide(Gravity.BOTTOM)//.setDuration(400)
        } else if (animateRightLeft) {
            fragment.enterTransition = Slide(Gravity.END)
            fragment.exitTransition = Slide(Gravity.START)
        }
        supportFragmentManager
            .beginTransaction()
            .add(containerId, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }

}