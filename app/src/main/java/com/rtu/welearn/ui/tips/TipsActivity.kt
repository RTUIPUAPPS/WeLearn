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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)

        binding?.btnOnline?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsOnline.size)
            showMessageDialog(
                this,
                true,
                getString(R.string.tips_online),
                listTipsOnline[randomIndex].tip.toString(),
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }
        binding?.btnOffline?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsOffline.size)
            showMessageDialog(
                this,true,
                getString(R.string.tips_offline),
                listTipsOffline[randomIndex].tip.toString(),
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }
        binding?.btnBoth?.setOnClickListener {
            val randomIndex = kotlin.random.Random.nextInt(listTipsBoth.size)
            showMessageDialog(
                this,true,
                getString(R.string.tips_online_offline),
                listTipsBoth[randomIndex].tip.toString(),
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }

    }

}