package com.rtu.welearn.ui.firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.databinding.ActivityFirebaseMainBinding
import com.rtu.welearn.ui.about.AboutActivity
import com.rtu.welearn.ui.tips.TipsActivity

class FirebaseMainActivity : BaseActivity() {
    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, FirebaseMainActivity::class.java)
            return intent
        }
    }

    var binding:ActivityFirebaseMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_firebase_main)

        binding?.btnAbout?.setOnClickListener {
            launchActivity(AboutActivity.getIntent(this))
        }
        binding?.btnTips?.setOnClickListener {
            launchActivity(TipsActivity.getIntent(this))
        }
    }
}