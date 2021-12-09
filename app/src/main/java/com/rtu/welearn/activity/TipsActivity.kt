package com.rtu.welearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.R
import com.rtu.welearn.databinding.ActivityAboutBinding
import com.rtu.welearn.databinding.ActivityDetailsBinding
import com.rtu.welearn.databinding.ActivityTipsBinding

class TipsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TipsActivity::class.java)
            return intent
        }
    }

    var binding: ActivityTipsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)
       
    }

}