package com.rtu.welearn.ui.tips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp
import com.rtu.welearn.WeLearnApp.Companion.dbVersionData
import com.rtu.welearn.WeLearnApp.Companion.roomDB
import com.rtu.welearn.WeLearnApp.Companion.tipsVersionFirebase
import com.rtu.welearn.data.room.tips.TipsData
import com.rtu.welearn.databinding.ActivityTipsBinding
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.showMessageDialog
import kotlinx.coroutines.launch

class TipsActivity : BaseActivity() {

    companion object {
        var pos = 0
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, TipsActivity::class.java)
        }
    }

    private var binding: ActivityTipsBinding? = null
    private var listTipsOnline = ArrayList<TipsData>()
    private var listTipsOffline = ArrayList<TipsData>()
    private var listTipsBoth = ArrayList<TipsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)

        if (dbVersionData.version_tips == tipsVersionFirebase) {
            getTipsFromLocalDB()

        } else {
            lifecycleScope.launch {
                getTipsFromFirebase()
            }
        }

        binding?.btnOnline?.setOnClickListener {
            if (listTipsOnline.isNotEmpty()) {
                val randomIndex = kotlin.random.Random.nextInt(listTipsOnline.size)
                showMessageDialog(
                    this,
                    true,
                    getString(R.string.tips_online),
                    listTipsOnline[randomIndex].tip
                )
            }
        }
        binding?.btnOffline?.setOnClickListener {
            if (listTipsOffline.isNotEmpty()) {

                val randomIndex = kotlin.random.Random.nextInt(listTipsOffline.size)
                showMessageDialog(
                    this, true,
                    getString(R.string.tips_offline),
                    listTipsOffline[randomIndex].tip
                )
            }
        }
        binding?.btnBoth?.setOnClickListener {
            if (listTipsBoth.isNotEmpty()) {
                val randomIndex = kotlin.random.Random.nextInt(listTipsBoth.size)
                showMessageDialog(
                    this, true,
                    getString(R.string.tips_online_offline),
                    listTipsBoth[randomIndex].tip
                )
            }
        }

    }

    private fun getTipsFromLocalDB() {

        lifecycleScope.launch {
            listTipsOnline =
                roomDB.tipsDao().getAllTips(Constants.TIPS_ONLINE) as ArrayList<TipsData>
        }
        lifecycleScope.launch {
            listTipsOffline =
                roomDB.tipsDao().getAllTips(Constants.TIPS_OFFLINE) as ArrayList<TipsData>
        }

        lifecycleScope.launch {
            listTipsBoth = roomDB.tipsDao().getAllTips(Constants.TIPS_BOTH) as ArrayList<TipsData>
        }
        binding?.progressBar?.visibility = View.GONE
    }

    private suspend fun getTipsFromFirebase() {
        WeLearnApp.mDatabase?.child(Constants.TIPS)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    roomDB.tipsDao().deleteTips()
                    snapshot.children.forEach {
                        it.children.forEach { data ->
                            lifecycleScope.launch {
                                pos += 1
                                Log.e("#TIPS_POSSS", "$pos")
                                roomDB.tipsDao().insertAllTips(
                                    TipsData(
                                        pos,
                                        type = it.key.toString(),
                                        tip = data.value.toString()
                                    )
                                )
                            }
                        }
                    }

                    dbVersionData.version_tips = tipsVersionFirebase
                    lifecycleScope.launch {
                        roomDB.dbVersionDao().updateVersion(dbVersionData)
                        getTipsFromLocalDB()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}