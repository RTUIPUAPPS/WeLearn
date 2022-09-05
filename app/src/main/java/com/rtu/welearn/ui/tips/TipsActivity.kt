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
import com.hitesh.weatherlogger.view.callback.ItemClickListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp
import com.rtu.welearn.WeLearnApp.Companion.dbVersionData
import com.rtu.welearn.WeLearnApp.Companion.dbVersionImpl
import com.rtu.welearn.WeLearnApp.Companion.roomDB
import com.rtu.welearn.WeLearnApp.Companion.tipsVersionFirebase
import com.rtu.welearn.WeLearnApp.Companion.tipsVersionLocalDB
import com.rtu.welearn.data.room.AppDatabase
import com.rtu.welearn.data.room.tips.TipsData
import com.rtu.welearn.data.tips.TipsDataImpl
import com.rtu.welearn.databinding.ActivityTipsBinding
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.showMessageDialog
import kotlinx.coroutines.launch

class TipsActivity : BaseActivity() {

    companion object {
        var pos = 0
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TipsActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityTipsBinding? = null

    private var listTipsOnline = ArrayList<TipsData>()
    private var listTipsOffline = ArrayList<TipsData>()
    private var listTipsBoth = ArrayList<TipsData>()
    private lateinit var tipsImpl: TipsDataImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)
        tipsImpl = TipsDataImpl(WeLearnApp.sqlDelightDB)

        if (dbVersionData?.version_tips == tipsVersionFirebase) {
            getTipsFromLocalDB()
            Log.e("#TIPS","getTipsFromLocalDB()")

        } else {
            lifecycleScope.launch {
                getTipsFromFirebase()
                Log.e("#TIPS","getTipsFromFirebase()")
            }
        }

        binding?.btnOnline?.setOnClickListener {
            if (listTipsOnline.isNotEmpty()) {
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
        }
        binding?.btnOffline?.setOnClickListener {
            if (listTipsOffline.isNotEmpty()) {

                val randomIndex = kotlin.random.Random.nextInt(listTipsOffline.size)
                showMessageDialog(
                    this, true,
                    getString(R.string.tips_offline),
                    listTipsOffline[randomIndex].tip.toString(),
                    object : ItemClickListener {
                        override fun onClick(status: Boolean) {

                        }
                    })
            }
        }
        binding?.btnBoth?.setOnClickListener {
            if (listTipsBoth.isNotEmpty()) {
                val randomIndex = kotlin.random.Random.nextInt(listTipsBoth.size)
                showMessageDialog(
                    this, true,
                    getString(R.string.tips_online_offline),
                    listTipsBoth[randomIndex].tip.toString(),
                    object : ItemClickListener {
                        override fun onClick(status: Boolean) {

                        }
                    })
            }
        }

    }

    private fun getTipsFromLocalDB() {

        lifecycleScope.launch {
            listTipsOnline.addAll(
                roomDB.tipsDao().getAllTips(Constants.TIPS_ONLINE) as ArrayList<TipsData>
            )
            Log.e("#TIPS","listTipsOnline ${listTipsOnline.size}")
        }
        lifecycleScope.launch {
            listTipsOffline.addAll(
                roomDB.tipsDao().getAllTips(Constants.TIPS_OFFLINE) as ArrayList<TipsData>
            )

            Log.e("#TIPS","listTipsOffline ${listTipsOffline.size}")
        }
        lifecycleScope.launch {

            listTipsBoth.addAll(
                roomDB.tipsDao().getAllTips(Constants.TIPS_BOTH) as ArrayList<TipsData>
            )

            Log.e("#TIPS","listTipsBoth ${listTipsBoth.size}")
            binding?.progressBar?.visibility = View.GONE
        }

//        lifecycleScope.launch {
//            tipsImpl.getTipsByType(Constants.TIPS_ONLINE).collect {
//                listTipsOnline.addAll(it)
//            }
//        }
//        lifecycleScope.launch {
//
//            tipsImpl.getTipsByType(Constants.TIPS_OFFLINE).collect {
//                listTipsOffline.addAll(it)
//            }
//        }
//        lifecycleScope.launch {
//
//            tipsImpl.getTipsByType(Constants.TIPS_BOTH).collect {
//                listTipsBoth.addAll(it)
//                binding?.progressBar?.visibility = View.GONE
//            }
//
//        }
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

                    dbVersionData?.version_tips=tipsVersionFirebase.toInt()
                    lifecycleScope.launch {
                        roomDB.dbVersionDao().updateVersion(dbVersionData!!)
                        Log.e("#TIPS","updateVersion $tipsVersionFirebase")
//                        dbVersionImpl?.updateTipsVersion(
//                            tipsVersionFirebase.toLong()
//                        )
                        getTipsFromLocalDB()
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}