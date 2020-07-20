package com.precticle.androidarchitecture.view.view.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.precticle.androidarchitecture.R
import com.precticle.androidarchitecture.paytm.ErrorResponse
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.ResponseBody
import kotlin.coroutines.CoroutineContext

open class BaseActivity() : AppCompatActivity(), CoroutineScope{
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main +  mJob

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_base)
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
    open fun showProgress() {
        progressBar.setVisibility(View.VISIBLE)
    }

    open fun toggleProgress(isLoading: Boolean) {
        if (isLoading) showProgress() else hideProgress()
    }

    open fun hideProgress() {
        progressBar.setVisibility(View.GONE)
    }

    open fun handleError(throwable: Throwable?) {
        showErrorDialog(getString(R.string.msg_unknown))
    }

    open fun handleUnknownError() {
        showErrorDialog(getString(R.string.msg_unknown))
    }

    open fun handleError(responseBody: ResponseBody?) {
        var message: String? = null
        if (responseBody != null) {
            try {
                val errorResponse: ErrorResponse = Gson().fromJson<ErrorResponse>(
                    responseBody.charStream(),
                    ErrorResponse::class.java
                )
                message = errorResponse.error
            } catch (e: JsonSyntaxException) {
            } catch (e: JsonIOException) {
            }
        }
        message = if (TextUtils.isEmpty(message)) getString(R.string.msg_unknown) else message
        showErrorDialog(message)
    }
    open fun showErrorDialog(message: String?) {
        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, { dialog, which -> })
            .show()
    }

    open fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    open fun hideToolbar() {
        app_bar.visibility = View.GONE
    }

    open fun changeStatusBarColor() {
        changeStatusBarColor(Color.WHITE)
    }

    open fun changeStatusBarColor(color: Int) {
        val window = window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = color
        }
    }
    open fun enableToolbarUpNavigation() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }
}