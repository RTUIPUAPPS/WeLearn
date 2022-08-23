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
            var intent = Intent(mContext, AboutActivity::class.java)
            return intent
        }
    }

    var binding: ActivityAboutBinding? = null
   // private var mCloudEndPoint: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)


//        mCloudEndPoint =  mDatabase?.child(ABOUT)
//        mCloudEndPoint?.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                binding?.tvAbout?.text = snapshot.value.toString()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
    }

}