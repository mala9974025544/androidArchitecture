package com.precticle.test.util

import android.content.Context
import android.content.SharedPreferences
import com.precticle.androidarchitecture.MyApplication
import com.precticle.androidarchitecture.R

object SharedPrefrence {

    private val TITLE = "title"
    private val AUTHTOKEN = "authtoken"
    private var singleTonInstance: SharedPrefrence? = null
    private val sharedPreferences: SharedPreferences? = null
    private val editor: SharedPreferences.Editor? = null
    private fun getSharedPref(context: Context): SharedPreferences? {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    //set title
    fun setTitle(context : Context,title : String){
        val sharedPref = getSharedPref(context)
        val editor = sharedPref!!.edit()
        editor.putString(TITLE, title)
        editor.apply()
    }
    //get Title
    fun getTitle(context :Context): String? {
        val sharedPref = getSharedPref(context)
        return sharedPref!!.getString(TITLE, "")
    }
    fun saveAuthToken(authToken: String?,context: Context) {

        val sharedPref = getSharedPref(context)
        val editor = sharedPref!!.edit()
        editor.putString(AUTHTOKEN, authToken)
        editor.apply()
    }



    fun getAuthToken(context: Context): String? {
        val sharedPref = SharedPrefrence.getSharedPref(context)
        return sharedPref!!.getString(SharedPrefrence.AUTHTOKEN, "")
    }

}
