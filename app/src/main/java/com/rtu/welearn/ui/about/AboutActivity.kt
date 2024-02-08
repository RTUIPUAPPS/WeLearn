package com.rtu.welearn.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.R
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, AboutActivity::class.java)
        }
    }

    var binding: ActivityAboutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
    }
}