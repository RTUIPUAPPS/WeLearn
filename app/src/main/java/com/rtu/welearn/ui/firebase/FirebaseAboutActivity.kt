package com.rtu.welearn.ui.firebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.R
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.WeLearnApp.Companion.mDatabase
import com.rtu.welearn.utils.Constants.Companion.ABOUT
import com.rtu.welearn.databinding.ActivityAboutBinding
import com.rtu.welearn.databinding.ActivityFirebaseAboutBinding

class FirebaseAboutActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, FirebaseAboutActivity::class.java)
            return intent
        }
    }

    var binding: ActivityFirebaseAboutBinding? = null
    private var mCloudEndPoint: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firebase_about)


        mCloudEndPoint =  mDatabase?.child(ABOUT)
        mCloudEndPoint?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding?.etAbout?.setText(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding?.btnSubmit?.setOnClickListener {
            mCloudEndPoint?.setValue(binding?.etAbout?.text)
        }
    }

}