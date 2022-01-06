package com.rtu.welearn.ui.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp.Companion.mDatabase
import com.rtu.welearn.databinding.ActivityTestBinding
import com.rtu.welearn.utils.Constants.Companion.TEST
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class TestActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TestActivity::class.java)
            return intent
        }
    }

    var binding: ActivityTestBinding? = null

    private var mCloudEndPoint: DatabaseReference? = null
    private var listQuestions = ArrayList<ModelTestQuestion>()
    private var listQuestionsTemp = ArrayList<ModelTestQuestion>()
    private var currentPos = 1
    private var pointsCollected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)


        mCloudEndPoint = mDatabase?.child(TEST)
        mCloudEndPoint?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d("snapshot", "${it.key},:: ${it.value}")
                    var model =
                        Gson().fromJson(Gson().toJson(it.value), ModelTestQuestion::class.java)
                    listQuestions.add(model)
                }

                val testAdapter = MyAdapter(mContext, listQuestions)
                binding?.viewPagerQuestions?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                binding?.viewPagerQuestions?.adapter = testAdapter
                selectRandomQuestions()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        initClick()
        initViewPageChangeCallback()

    }

    private fun initClick() {
        binding?.btnFinish?.setOnClickListener {
            if (currentPos == 9) {

            } else {
                binding?.viewPagerQuestions?.setCurrentItem(currentPos+1, true)
            }
        }
    }

    private fun initViewPageChangeCallback() {
        binding?.viewPagerQuestions?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos = position
                binding?.tvRemaining?.text = "${currentPos+1}/10"

                if (currentPos == 10) {
                    binding?.btnFinish?.text = getString(R.string.finish)
                }

            }

        })
    }


    private fun selectRandomQuestions(){


        val randomIndex = kotlin.random.Random.nextInt(listQuestions.size);
        val randomElement = listQuestions[randomIndex]
        listQuestionsTemp.add(randomElement)

        // here we can use the selected element to print it for example
        println("#Random: "+randomElement)
    }

}