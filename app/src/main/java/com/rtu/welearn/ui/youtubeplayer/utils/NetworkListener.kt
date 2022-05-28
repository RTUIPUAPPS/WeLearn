package com.rtu.welearn.ui.youtubeplayer.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rtu.welearn.ui.youtubeplayer.utils.Utils

internal class NetworkListener : BroadcastReceiver() {

    var onNetworkUnavailable = { }
    var onNetworkAvailable = { }

    override fun onReceive(context: Context, intent: Intent) {
        if (Utils.isOnline(context))
            onNetworkAvailable()
        else
            onNetworkUnavailable()
    }
}
