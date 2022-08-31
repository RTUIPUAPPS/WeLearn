package com.rtu.welearn.ui.test

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
import com.rtu.welearn.WeLearnApp.Companion.testVersionFirebase
import com.rtu.welearn.WeLearnApp.Companion.testVersionLocalDB
import com.rtu.welearn.data.test_data_source.TestDataSourceImpl
import com.rtu.welearn.databinding.ActivityTestBinding
import com.rtu.welearn.utils.AppUtils.showToastShort
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.showMessageDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import welearndb.TestEntity

class TestActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, TestActivity::class.java)
        }
    }

    var binding: ActivityTestBinding? = null
    private var listTestQuestions = listOf<TestEntity>()
    private var listAllQuestions = ArrayList<TestEntity>()
    private var listAllQuestionsTemp = ArrayList<TestEntity>()
    private var listExamQuestions = ArrayList<TestEntity>()
    private var currentPos = 0
    private val totalTestQuestions = 10
    private var arrayAnswers = HashMap<Int, Int>()
    var testImpl: TestDataSourceImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        testImpl = TestDataSourceImpl(WeLearnApp.sqlDelightDB)

        if (testVersionLocalDB == testVersionFirebase) {
            getQuestionsList()
        } else {
            lifecycleScope.launch {
                getTestQuestionsFromFirebase()
            }
        }

        initClick()

    }


    private fun getQuestionsList() {

        lifecycleScope.launch {
            testImpl?.getAllQuestions()?.collect {
                listTestQuestions = it
                listAllQuestions.clear()
                listAllQuestionsTemp.clear()
                listAllQuestionsTemp.addAll(listTestQuestions)
                listAllQuestions.addAll(listTestQuestions)
                selectRandomQuestions()
            }
        }
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
                            "Good! You know the basics but there is still a lot of room for improvement. To improve your knowledge about neighbornes, we highly recommend you to check other WeLearn app functions and to visit our website at: <br>http://welearn-project.eu/"
                    }
                    in 11..25 -> {
                        message =
                            "Very well! You have sufficient knowledge about intercultural learning environment, but there is still room for improvement. Click back and find ways to improve your knowledge. You can also gain more info form our website: <br>http://welearn-project.eu/"
                    }
                    in 26..30 -> {
                        message =
                            "Excellent! You reached the highest score! The test results are showing adequate know-how about neighbornes and how to work in a multicultural learning environment! If you want to learn even more, we recommend you to check the material on the app and our Website: <br> http://welearn-project.eu/ "
                    }
                }
                showMessageDialog(
                    this,
                    false,
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

    private suspend fun getTestQuestionsFromFirebase() {
        WeLearnApp.mDatabase
            ?.child(Constants.TEST)
            ?.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    testImpl?.deleteAllQuestions()

                    snapshot.children.forEach {
                        CoroutineScope(Dispatchers.IO).launch {
                            testImpl?.insertQuestion(
                                Point1 = it.child("Points1").value.toString(),
                                Point2 = it.child("Points2").value.toString(),
                                Point3 = it.child("Points3").value.toString(),
                                Point4 = it.child("Points4").value.toString(),
                                Point5 = it.child("Points5").value.toString(),
                                Question = it.child("Question").value.toString(),
                                Answer1 = it.child("Reply1").value.toString(),
                                Answer2 = it.child("Reply2").value.toString(),
                                Answer3 = it.child("Reply3").value.toString(),
                                Answer4 = it.child("Reply4").value.toString(),
                                Answer5 = it.child("Reply5").value.toString(),
                                id = null
                            )
                        }
                    }

                    lifecycleScope.launch {
                        WeLearnApp.dbVersionImpl?.updateTestQuestionsVersion(
                            testVersionFirebase.toLong()
                        )
                    }

                    getQuestionsList()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun selectRandomQuestions() {

        if (listAllQuestionsTemp.isNotEmpty()) {

            Log.e("listAllQuestionsTemp", "${listAllQuestionsTemp.size}")
            Log.e("listAllQuestionsTemp", "${listAllQuestionsTemp.toArray()}")
            for (i in 0 until totalTestQuestions) {
                val randomIndex = kotlin.random.Random.nextInt(0, listAllQuestionsTemp.size - 1)
                val randomElement = listAllQuestionsTemp[randomIndex]
                listExamQuestions.add(randomElement)
                listAllQuestionsTemp.remove(randomElement)
            }

            showQuestion(0)
            binding?.progressBarCircle?.visibility = View.GONE
            binding?.llTest?.visibility = View.VISIBLE
            binding?.btnFinish?.visibility = View.VISIBLE
        }
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
            binding?.rbAns1?.text = model.Answer1 ?: ""
            binding?.rbAns2?.text = model.Answer2 ?: ""
            binding?.rbAns3?.text = model.Answer3 ?: ""
            binding?.rbAns4?.text = model.Answer4 ?: ""
            binding?.rbAns5?.text = model.Answer5 ?: ""

        }
    }
}