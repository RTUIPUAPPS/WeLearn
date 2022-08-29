package com.rtu.welearn

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.rtu.welearn.data.db_version.DBVersionDataSourceImpl
import com.rtu.welearn.data.test_data_source.TestDataSourceImpl
import com.rtu.welearn.data.video_list.VideoListDataSourceImpl
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.TIPS_BOTH
import com.rtu.welearn.utils.Constants.Companion.TIPS_OFFLINE
import com.rtu.welearn.utils.Constants.Companion.TIPS_ONLINE
import com.rtu.welearn.utils.Constants.Companion.VIDEO_DESCRIPTION
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_TITLE
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import welearndb.TestEntity
import welearndb.VideoListEntity

class WeLearnApp : Application() {
    companion object {
        var driver: SqlDriver? = null
        var dbVersionLocal = 0
        var dbVersionFirebase = 0

        var testImpl: TestDataSourceImpl? = null
        var dbVersionImpl: DBVersionDataSourceImpl? = null
        var videoListImpl: VideoListDataSourceImpl? = null

        var mDatabase: DatabaseReference? = null
        private var mCloudEndPointVideoList: DatabaseReference? = null

        var videoList = listOf<VideoListEntity>()
        var listTestQuestions = listOf<TestEntity>()
        var _isLoadingQuestions = MutableLiveData<Boolean>(true)

        var listTipsOnline = ArrayList<String>()
        var listTipsOffline = ArrayList<String>()
        var listTipsBoth = ArrayList<String>()
    }

    override fun onCreate() {
        super.onCreate()
        mDatabase = FirebaseDatabase.getInstance().reference
        CoroutineScope(Dispatchers.Main).launch {
            getLocalDBVersion()
        }

        driver = AndroidSqliteDriver(WeLearnDatabase.Schema, this, "welearn.db")
        val sqlDelightDB = WeLearnDatabase(driver!!)
        testImpl = TestDataSourceImpl(sqlDelightDB)
        dbVersionImpl = DBVersionDataSourceImpl(sqlDelightDB)
        videoListImpl = VideoListDataSourceImpl(sqlDelightDB)
        getTipsFromFirebase()
    }

    private suspend fun getLocalDBVersion() {
        dbVersionImpl?.getLocalDBVersion()?.collect(FlowCollector {
            if (it.isNotEmpty()) {
                dbVersionLocal = it[0].version?.toInt() ?: 0
            }
            getFirebaseDBVersion()

        })
    }

    private fun getFirebaseDBVersion() {
        mDatabase?.child(Constants.FIREBASE_DB_VERSION)?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dbVersionFirebase = snapshot.value.toString().toInt()
                    if (dbVersionLocal != dbVersionFirebase) {
                        CoroutineScope(Dispatchers.IO).launch {
                            getTestQuestionsFromFirebase()
                            getVideoListFromFirebase()
                        }
                    } else {
                        /**set value from local db*/
                        listTestQuestions = testImpl?.getAllQuestions() ?: listOf()
                        videoList = videoListImpl?.getVideoList() ?: listOf()
                        _isLoadingQuestions.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private suspend fun getTestQuestionsFromFirebase() {
        mDatabase
            ?.child(Constants.TEST)
            ?.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    testImpl?.deleteAllQuestions()
                    CoroutineScope(Dispatchers.Main).launch {
                        _isLoadingQuestions.value = true

                    }
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

                    CoroutineScope(Dispatchers.Main).launch {
                         listTestQuestions = testImpl?.getAllQuestions() ?: listOf()
                         _isLoadingQuestions.value = false
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private  fun getTipsFromFirebase() {
        mDatabase?.child(Constants.TIPS)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {

                        when (it.key.toString()) {
                            TIPS_ONLINE -> {
                                it.children.forEach { data ->
                                    listTipsOnline.add(data.value.toString())
                                }

                            }
                            TIPS_OFFLINE -> {
                                it.children.forEach { data ->
                                    listTipsOffline.add(data.value.toString())
                                }
                            }
                            TIPS_BOTH -> {
                                it.children.forEach { data ->
                                    listTipsBoth.add(data.value.toString())
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

    private suspend fun getVideoListFromFirebase() {
        mCloudEndPointVideoList = mDatabase?.child(Constants.VIDEO_LIST)
        mCloudEndPointVideoList?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    CoroutineScope(Dispatchers.Main).launch {
                        videoListImpl?.insertVideoDetails(
                            null,
                            it.child(VIDEO_ID).value.toString(),
                            it.child(VIDEO_TITLE).value.toString(),
                            it.child(VIDEO_DESCRIPTION).value.toString()
                        )
                    }
                }
                videoList = videoListImpl?.getVideoList() ?: listOf()
                CoroutineScope(Dispatchers.Main).launch {
                    dbVersionImpl?.setLocalDBVersion(dbVersionFirebase.toLong())
                }
              }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}