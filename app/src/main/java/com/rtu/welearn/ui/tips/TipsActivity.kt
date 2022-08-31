package com.rtu.welearn.ui.tips

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.rtu.welearn.WeLearnApp.Companion.dbVersionImpl
import com.rtu.welearn.WeLearnApp.Companion.tipsVersionFirebase
import com.rtu.welearn.WeLearnApp.Companion.tipsVersionLocalDB
import com.rtu.welearn.data.tips.TipsDataImpl
import com.rtu.welearn.databinding.ActivityTipsBinding
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.showMessageDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import welearndb.TipsEntity

class TipsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TipsActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityTipsBinding? = null

    private var listTipsOnline = ArrayList<TipsEntity>()
    private var listTipsOffline = ArrayList<TipsEntity>()
    private var listTipsBoth = ArrayList<TipsEntity>()
    private lateinit var tipsImpl: TipsDataImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips)
        tipsImpl = TipsDataImpl(WeLearnApp.sqlDelightDB)

        if (tipsVersionLocalDB == tipsVersionFirebase) {
            getTipsFromLocalDB()

        } else {
            lifecycleScope.launch {
                getTipsFromFirebase()
            }
        }

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
                this, true,
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
                this, true,
                getString(R.string.tips_online_offline),
                listTipsBoth[randomIndex].tip.toString(),
                object : ItemClickListener {
                    override fun onClick(status: Boolean) {

                    }
                })

        }

    }

    private fun getTipsFromLocalDB() {
        lifecycleScope.launch {
            tipsImpl.getTipsByType(Constants.TIPS_ONLINE).collect {
                listTipsOnline.addAll(it)
            }
        }
        lifecycleScope.launch {

            tipsImpl.getTipsByType(Constants.TIPS_OFFLINE).collect {
                listTipsOffline.addAll(it)
            }
        }
        lifecycleScope.launch {

            tipsImpl.getTipsByType(Constants.TIPS_BOTH).collect {
                listTipsBoth.addAll(it)
                binding?.progressBar?.visibility = View.GONE
            }

        }
    }

    private suspend fun getTipsFromFirebase() {
        WeLearnApp.mDatabase?.child(Constants.TIPS)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        when (it.key.toString()) {
                            Constants.TIPS_ONLINE -> {
                                it.children.forEach { data ->

                                    CoroutineScope(Dispatchers.IO).launch {
                                        tipsImpl.insertTip(
                                            null,
                                            it.key.toString(),
                                            data.value.toString()
                                        )
                                    }

                                }

                            }
                            Constants.TIPS_OFFLINE -> {
                                it.children.forEach { data ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        tipsImpl.insertTip(
                                            null,
                                            it.key.toString(),
                                            data.value.toString()
                                        )
                                    }
                                }

                            }
                            Constants.TIPS_BOTH -> {
                                it.children.forEach { data ->

                                    CoroutineScope(Dispatchers.IO).launch {
                                        tipsImpl.insertTip(
                                            null,
                                            it.key.toString(),
                                            data.value.toString()
                                        )
                                    }
                                }
                            }
                        }


                    }
                    lifecycleScope.launch {
                        dbVersionImpl?.updateTipsVersion(
                            tipsVersionFirebase.toLong()
                        )
                    }
                    getTipsFromLocalDB()

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}