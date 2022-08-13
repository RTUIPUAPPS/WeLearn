package com.rtu.welearn.ui.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.hitesh.weatherlogger.view.callback.ItemClickListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp.Companion._isLoadingQuestions
import com.rtu.welearn.WeLearnApp.Companion.testImpl
import com.rtu.welearn.databinding.ActivityTestBinding
import com.rtu.welearn.utils.AppUtils.showToastShort
import com.rtu.welearn.utils.showMessageDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import welearndb.TestEntity

class TestActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, TestActivity::class.java)
        }
    }

    var binding: ActivityTestBinding? = null
    private var listAllQuestions = ArrayList<TestEntity>()
    private var listAllQuestionsTemp = ArrayList<TestEntity>()
    private var listExamQuestions = ArrayList<TestEntity>()
    private var currentPos = 0
    private val totalTestQuestions = 10
    private var arrayAnswers = HashMap<Int, Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)

        _isLoadingQuestions.observe(this, {
            if (it) {
                CoroutineScope(Dispatchers.IO).launch {
                    getQuestionsList()

                }
            }
        })
        initClick()
        initViewPageChangeCallback()

    }


    private suspend fun getQuestionsList() {
        testImpl?.getAllQuestions()?.collect(FlowCollector {
            listAllQuestions.addAll(it)
            listAllQuestionsTemp.addAll(it)
            selectRandomQuestions()
        })
//        val dbList = WeLearnApp.testQueries?.getAllQuestions()?.executeAsList()
//        dbList?.let {
//
//        }


    }

    private fun initClick() {
        binding?.btnFinish?.setOnClickListener {

            val checkedId = binding?.rgAnswers?.checkedRadioButtonId
            var selectedPoint = 0
            var isAnswered = false
            when {
                binding?.rbAns1?.id == checkedId -> {
                    selectedPoint = listExamQuestions[currentPos].Points1!!.toInt()
                    arrayAnswers[currentPos] = selectedPoint
                }
                binding?.rbAns2?.id == checkedId -> {
                    selectedPoint = listExamQuestions[currentPos].Points2!!.toInt()
                    arrayAnswers[currentPos] = selectedPoint
                }
                binding?.rbAns3?.id == checkedId -> {
                    selectedPoint = listExamQuestions[currentPos].Points3!!.toInt()
                    arrayAnswers[currentPos] = selectedPoint
                }
                binding?.rbAns4?.id == checkedId -> {
                    selectedPoint = listExamQuestions[currentPos].Points4!!.toInt()
                    arrayAnswers[currentPos] = selectedPoint
                }
                binding?.rbAns5?.id == checkedId -> {
                    selectedPoint = listExamQuestions[currentPos].Points5!!.toInt()
                    arrayAnswers[currentPos] = selectedPoint
                }
                else -> {
                    showToastShort("Please select any option.")
                }
            }
            when (selectedPoint) {
                1 -> {
                    showToastShort("Room for improvement - 1 point")
                    currentPos += 1
                    isAnswered = true
                }
                2 -> {
                    showToastShort("Good! - 2 points")
                    currentPos += 1
                    isAnswered = true
                }
                3 -> {
                    showToastShort(" Excellent! - 3 points")
                    currentPos += 1
                    isAnswered = true
                }
            }
            if (currentPos == 10 && isAnswered) {
                var points = 0
                arrayAnswers.forEach {
                    points += it.value
                }

                var message = ""
                when (points) {
                    in 0..10 -> {
                        message =
                            "Good! You know the basics but there is still a lot of room for improvement. To improve your knowledge about neighbornes, we highly recommend you to check other WeLearn app functions and to visit our website at: http://welearn-project.eu/"
                    }
                    in 11..25 -> {
                        message =
                            "Very well! You have sufficient knowledge about intercultural learning environment, but there is still room for improvement. Click back and find ways to improve your knowledge. You can also gain more info form our website: http://welearn-project.eu/"
                    }
                    in 26..30 -> {
                        message =
                            "Excellent! You reached the highest score! The test results are showing adequate know-how about neighbornes and how to work in a multicultural learning environment! If you want to learn even more, we recommend you to check the material on the app and our Website: http://welearn-project.eu/ "
                    }
                }
                showMessageDialog(
                    this,
                    getString(R.string.test_result),
                    "$points Points : $message",
                    object : ItemClickListener {
                        override fun onClick(status: Boolean) {
                            finish()
                        }
                    })
            } else {
                showQuestion(currentPos)
            }
        }
    }

    private fun initViewPageChangeCallback() {
        binding?.viewPagerQuestions?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.progressBar?.progress = position + 1
                currentPos = position
                binding?.tvRemaining?.text = "${currentPos + 1}/$totalTestQuestions"

                if (currentPos == 9) {
                    binding?.btnFinish?.text = getString(R.string.finish)
                }
            }
        })
    }


    private fun selectRandomQuestions() {

        for (i in 0 until totalTestQuestions) {
            val randomIndex = kotlin.random.Random.nextInt(listAllQuestionsTemp.size)
            val randomElement = listAllQuestionsTemp[randomIndex]
            listExamQuestions.add(randomElement)
            listAllQuestionsTemp.remove(randomElement)
        }

        showQuestion(0)
        binding?.progressBarCircle?.visibility = View.GONE
        binding?.llTest?.visibility = View.VISIBLE
        binding?.btnFinish?.visibility = View.VISIBLE
    }


    private fun showQuestion(position: Int) {
        binding?.progressBar?.progress = position + 1
        binding?.rgAnswers?.clearCheck()
        if (listExamQuestions.size > 0) {

            binding?.tvRemaining?.text = "${position + 1}/$totalTestQuestions"
            binding?.progressBar?.progress = position + 1
            val model = listExamQuestions[position]
            if (model.Answer4.isNullOrEmpty()) {
                binding?.rbAns4?.visibility = View.GONE
            } else {
                binding?.rbAns4?.visibility = View.VISIBLE
            }

            if (model.Answer5.isNullOrEmpty()) {
                binding?.rbAns5?.visibility = View.GONE
            } else {
                binding?.rbAns5?.visibility = View.VISIBLE
            }
            binding?.tvQuestion?.text = model.Question
            binding?.rbAns1?.text = model.Answer1
            binding?.rbAns2?.text = model.Answer2
            binding?.rbAns3?.text = model.Answer3
            binding?.rbAns4?.text = model.Answer4
            binding?.rbAns5?.text = model.Answer5

        }
    }
}