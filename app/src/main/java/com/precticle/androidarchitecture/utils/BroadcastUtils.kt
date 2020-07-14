package com.precticle.androidarchitecture.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager

object BroadcastUtils {

    //Define Actions
    const val ACTION_SEND = "action_send"
    const val ACTION_SEND_STR = "action_send_str"



    fun broadCastSend(context: Context, str:String) {
        val intent = Intent(BroadcastUtils.ACTION_SEND)
        intent.putExtra(BroadcastUtils.ACTION_SEND_STR, str)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}