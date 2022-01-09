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
import com.rtu.welearn.utils.AppUtils.showToast
import com.rtu.welearn.utils.Constants.Companion.TEST

class TestActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, TestActivity::class.java)
            return intent
        }
    }

    var binding: ActivityTestBinding? = null

    private var mCloudEndPoint: DatabaseReference? = null
    private var listAllQuestions = ArrayList<ModelTestQuestion>()
    private var listAllQuestionsTemp = ArrayList<ModelTestQuestion>()
    private var listExamQuestions = ArrayList<ModelTestQuestion>()
    private var currentPos = 1
    private var pointsCollected = 0
    private var arrayAnswers = HashMap<Int,Int>()
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
                    listAllQuestions.add(model)
                    listAllQuestionsTemp.add(model)
                }
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
                var points = 0
                arrayAnswers.forEach {
                    points += it.value
                }
                showToast(points.toString())

            } else {
                currentPos += 1
                binding?.viewPagerQuestions?.setCurrentItem(currentPos + 1, true)
                binding?.rgAnswers?.clearCheck()
//                showQuestion(currentPos)
            }
        }
    }

    private fun initViewPageChangeCallback() {
        binding?.viewPagerQuestions?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos = position
                binding?.tvRemaining?.text = "${currentPos + 1}/10"

                if (currentPos == 9) {
                    binding?.btnFinish?.text = getString(R.string.finish)
                }

            }
        })
    }


    private fun selectRandomQuestions() {

        for (i in 0..9) {
            val randomIndex = kotlin.random.Random.nextInt(listAllQuestionsTemp.size)
            val randomElement = listAllQuestionsTemp[randomIndex]
            listExamQuestions.add(randomElement)
            listAllQuestionsTemp.remove(randomElement)
            // here we can use the selected element to print it for example
            println("#Random: " + randomElement.Question)
        }

        val testAdapter = MyAdapter(mContext, listExamQuestions, object : interfaceAnswerSelected {
            override fun onAnswerSelected(position: Int, ans: Int) {
                arrayAnswers.put(position, ans)
            }
        })
        binding?.viewPagerQuestions?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding?.viewPagerQuestions?.adapter = testAdapter
        //showQuestion(0)

    }


    private fun showQuestion(position: Int) {
        if (listExamQuestions.size > 0) {
            binding?.tvRemaining?.text = "$position/${listExamQuestions.size}"
            val model = listExamQuestions[position]


            binding?.tvQuestion?.text = model.Question
            binding?.rbAns1?.text = model.Reply1
            binding?.rbAns2?.text = model.Reply2
            binding?.rbAns3?.text = model.Reply3
            binding?.rbAns4?.text = model.Reply4
            binding?.rbAns5?.text = model.Reply5

        }
    }
}