package com.rtu.welearn.ui.tips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitesh.weatherlogger.view.callback.ItemClickListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp.Companion.listTipsBoth
import com.rtu.welearn.WeLearnApp.Companion.listTipsOffline
import com.rtu.welearn.WeLearnApp.Companion.listTipsOnline
import com.rtu.welearn.databinding.ActivityTipsBinding
import com.rtu.welearn.utils.showMessageDialog

class TipsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TipsActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityTipsBinding? = null

    private lateinit var ownerAdapter: TipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)
        ownerAdapter = TipsAdapter()
        binding?.rvTips.apply {
            this?.adapter = ownerAdapter
            this?.layoutManager = LinearLayoutManager(mContext)
        }

//        mCloudEndPoint?.child(TIPS_ONLINE)?.setValue(TIPS_ONLINE)
//        mCloudEndPoint?.child(TIPS_OFFLINE)?.setValue(TIPS_OFFLINE)
//        mCloudEndPoint?.child(TIPS_BOTH)?.setValue(TIPS_BOTH)


        binding?.btnOnline?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsOnline.size)
            showMessageDialog(
                this,
                getString(R.string.tips_online),
                listTipsOnline[randomIndex],
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }
        binding?.btnOffline?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsOffline.size)
            showMessageDialog(
                this,
                getString(R.string.tips_offline),
                listTipsOffline[randomIndex],
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }
        binding?.btnBoth?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsBoth.size)
            showMessageDialog(
                this,
                getString(R.string.tips_online_offline),
                listTipsBoth[randomIndex],
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }

    }

}