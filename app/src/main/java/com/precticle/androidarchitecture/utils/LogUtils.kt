package com.precticle.androidarchitecture.utils

import android.text.TextUtils
import android.util.Log
import com.precticle.androidarchitecture.BuildConfig


fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG || !CoreConstants.IS_RELEASE) {
            Log.d(tag, message)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG || !CoreConstants.IS_RELEASE) {
            if (!TextUtils.isEmpty(message))
                Log.e(tag, message)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG || !CoreConstants.IS_RELEASE) {
            Log.i(tag, message)
        }
    }

    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG || !CoreConstants.IS_RELEASE) {
            Log.v(tag, message)
        }
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG || !CoreConstants.IS_RELEASE) {
            Log.w(tag, message)
        }
    }

