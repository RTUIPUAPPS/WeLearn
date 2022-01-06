package com.rtu.welearn.ui.tips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.R
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.WeLearnApp.Companion.mDatabase
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.databinding.ActivityTipsBinding

class TipsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TipsActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityTipsBinding? = null

    private lateinit var ownerAdapter: TipsAdapter
    private var mCloudEndPoint: DatabaseReference? = null
    private var listTips=ArrayList<TipsResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)
        ownerAdapter = TipsAdapter()
        binding?.rvTips.apply {
            this?.adapter = ownerAdapter
            this?.layoutManager = LinearLayoutManager(mContext)
        }
        mCloudEndPoint = mDatabase?.child(Constants.TIPS)
//        mCloudEndPoint?.child(TIPS_ONLINE)?.setValue(TIPS_ONLINE)
//        mCloudEndPoint?.child(TIPS_OFFLINE)?.setValue(TIPS_OFFLINE)
//        mCloudEndPoint?.child(TIPS_BOTH)?.setValue(TIPS_BOTH)
        mCloudEndPoint?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d("snapshot","${it.key},:: ${it.value}")
                    listTips.add(TipsResponse(it.value.toString()))
                }
                ownerAdapter.differ.submitList(listTips)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}