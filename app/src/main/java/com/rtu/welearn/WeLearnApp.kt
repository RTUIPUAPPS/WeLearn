package com.rtu.welearn

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.rtu.welearn.data.test_data_source.TestDataSourceImpl
import com.rtu.welearn.ui.video_list.VideoDetails
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_DESCRIPTION
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_TITLE
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import welearndb.TestEntityQueries

class WeLearnApp : Application() {
    companion object {
        var driver: SqlDriver? = null
        var dbVersionLocal = 0
        var dbVersionFirebase = 0

        //        var sqlDelightDB: WeLearnDatabase? = null
        var testQueries: TestEntityQueries? = null
        var testImpl: TestDataSourceImpl? = null
        var mDatabas: FirebaseDatabase? = null
        var mDatabase: DatabaseReference? = null
        private var mCloudEndPointVideoList: DatabaseReference? = null
        var mCloudEndPointTestQuestions: DatabaseReference? = null
        var mCloudEndPointDBVersio: DatabaseReference? = null
        var VideoList = arrayListOf<VideoDetails>()
        var _isLoadingQuestions = MutableLiveData<Boolean>(false)
    }

    override fun onCreate() {
        super.onCreate()
        driver = AndroidSqliteDriver(WeLearnDatabase.Schema, this, "welearn.db")
        val sqlDelightDB = WeLearnDatabase(driver!!)
        testQueries = sqlDelightDB.testEntityQueries
        testImpl = TestDataSourceImpl(sqlDelightDB)

        mDatabas = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        mCloudEndPointDBVersio = mDatabase?.child(Constants.FIREBASE_DB_VERSION)
        mCloudEndPointDBVersio?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dbVersionFirebase = snapshot.value.toString().toInt()
                if (dbVersionLocal != dbVersionFirebase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        getTestQuestions()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        //Fetching video list fromFirebase
        mCloudEndPointVideoList = mDatabase?.child(Constants.VIDEO_LIST)
        mCloudEndPointVideoList?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    VideoList.add(
                        VideoDetails(
                            it.child(VIDEO_ID).value.toString(),
                            it.child(VIDEO_TITLE).value.toString(),
                            it.child(VIDEO_DESCRIPTION).value.toString()
                        )
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private suspend fun getTestQuestions() {
        mCloudEndPointTestQuestions = mDatabase?.child(Constants.TEST)
        mCloudEndPointTestQuestions?.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                testQueries?.deleteQuestions()
                snapshot.children.forEach {
                    CoroutineScope(Dispatchers.IO).launch {
                        testImpl?.insertQuestion(
                            point1 = it.child("Points1").value.toString(),
                            point2 = it.child("Points2").value.toString(),
                            point3 = it.child("Points3").value.toString(),
                            point4 = it.child("Points4").value.toString(),
                            point5 = it.child("Points5").value.toString(),
                            question = it.child("Question").value.toString(),
                            answer1 = it.child("Reply1").value.toString(),
                            answer2 = it.child("Reply2").value.toString(),
                            answer3 = it.child("Reply3").value.toString(),
                            answer4 = it.child("Reply4").value.toString(),
                            answer5 = it.child("Reply5").value.toString(),
                            id = null

                        )
                        CoroutineScope(Dispatchers.Main).launch {
                            _isLoadingQuestions.value = true
                        }
                    }
                }

                CoroutineScope(Dispatchers.Main).launch {
                    _isLoadingQuestions.value = false
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}