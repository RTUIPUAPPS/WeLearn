package com.rtu.welearn.ui.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.hitesh.weatherlogger.view.callback.ItemClickListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp.Companion.mDatabase
import com.rtu.welearn.databinding.ActivityTestBinding
import com.rtu.welearn.utils.AppUtils.showToast
import com.rtu.welearn.utils.AppUtils.showToastShort
import com.rtu.welearn.utils.Constants.Companion.TEST
import com.rtu.welearn.utils.showMessageDialog

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
    private var currentPos = 0
    private var pointsCollected = 0
    private var arrayAnswers = HashMap<Int, Int>()
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

    /*Domino - to prevent domestic violence
RTU IPU
*/
    private fun initClick() {
        binding?.btnFinish?.setOnClickListener {

            if (currentPos == 9) {
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
                val checkedId = binding?.rgAnswers?.checkedRadioButtonId
                var selectedPoint = 0
                when {
                    binding?.rbAns1?.id == checkedId -> {
                        selectedPoint = listExamQuestions[currentPos].Points1.toInt()
                        arrayAnswers[currentPos] = selectedPoint
                    }
                    binding?.rbAns2?.id == checkedId -> {
                        selectedPoint = listExamQuestions[currentPos].Points2.toInt()
                        arrayAnswers[currentPos] = selectedPoint
                    }
                    binding?.rbAns3?.id == checkedId -> {
                        selectedPoint = listExamQuestions[currentPos].Points3.toInt()
                        arrayAnswers[currentPos] = selectedPoint
                    }
                    binding?.rbAns4?.id == checkedId -> {
                        selectedPoint = listExamQuestions[currentPos].Points4.toInt()
                        arrayAnswers[currentPos] = selectedPoint
                    }
                    binding?.rbAns5?.id == checkedId -> {
                        selectedPoint = listExamQuestions[currentPos].Points5.toInt()
                        arrayAnswers[currentPos] = selectedPoint
                    }
                }

                when(selectedPoint){
                    1->{
                        showToastShort("Room for improvement - 1 point")
                    }
                    2->{
                        showToastShort("Good! - 2 points")
                    }
                    3->{
                        showToastShort(" Excellent! - 3 points")
                    }
                }
                currentPos += 1
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
        }

        showQuestion(0)
        binding?.progressBarCircle?.visibility = View.GONE
        binding?.llTest?.visibility = View.VISIBLE
    }


    private fun showQuestion(position: Int) {
        binding?.progressBar?.progress = position + 1
        binding?.rgAnswers?.clearCheck()
        if (listExamQuestions.size > 0) {

            binding?.tvRemaining?.text = "${position + 1}/${listExamQuestions.size}"
            binding?.progressBar?.progress = position + 1
            val model = listExamQuestions[position]
            if (model.Reply4.isEmpty()) {
                binding?.rbAns4?.visibility = View.GONE
            } else {
                binding?.rbAns4?.visibility = View.VISIBLE
            }

            if (model.Reply5.isNullOrEmpty()) {
                binding?.rbAns5?.visibility = View.GONE
            } else {
                binding?.rbAns5?.visibility = View.VISIBLE
            }
            binding?.tvQuestion?.text = model.Question
            binding?.rbAns1?.text = model.Reply1
            binding?.rbAns2?.text = model.Reply2
            binding?.rbAns3?.text = model.Reply3
            binding?.rbAns4?.text = model.Reply4
            binding?.rbAns5?.text = model.Reply5

        }
    }
}