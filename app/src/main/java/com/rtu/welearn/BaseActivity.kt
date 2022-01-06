package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    var mContext: Context? = null
    var mActivity: AppCompatActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this
    }

    fun launchActivity(intent: Intent) {
        startActivity(intent)
    }
}