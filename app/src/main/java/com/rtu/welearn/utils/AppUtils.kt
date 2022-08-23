package com.rtu.welearn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast


object AppUtils {

    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun Context.showToastShort(msg: String) {

        Toast.makeText(
            applicationContext,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun openUrl(mContext: Context?, url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        mContext?.startActivity(i)
    }

    /** check internet connectivity**/
    @SuppressLint("MissingPermission")
    fun isInternetAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }

//    fun showTipsDialog(strMessage:String,context:Context?){
//        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
//        builder.setMessage(strMessage)
//        builder.setCancelable(false)
//        builder  .setPositiveButton("Dismiss"
//        ) { dialog, p1 ->    dialog.cancel();   }
//
//        val alert = builder.create()
//        //Setting the title manually
//        //Setting the title manually
//
//        alert.show()
//    }

}