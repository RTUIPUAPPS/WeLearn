package com.rtu.welearn

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.rtu.welearn.data.db_version.DBVersionDataSourceImpl
import com.rtu.welearn.data.test_data_source.TestDataSourceImpl
import com.rtu.welearn.data.tips.TipsDataImpl
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.TIPS_BOTH
import com.rtu.welearn.utils.Constants.Companion.TIPS_OFFLINE
import com.rtu.welearn.utils.Constants.Companion.TIPS_ONLINE
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import welearndb.TestEntity
import welearndb.TipsEntity

class WeLearnApp : Application() {
    companion object {
        var driver: SqlDriver? = null
        //  var dbVersionLocal = 0

        var testVersionLocalDB = 0
        var tipsVersionLocalDB = 0
        var videoListVersionLocalDB = 0

        var testVersionFirebase = 0
        var tipsVersionFirebase = 0
        var videoListVersionFirebase = 0

        var testImpl: TestDataSourceImpl? = null
        var dbVersionImpl: DBVersionDataSourceImpl? = null
        lateinit var tipsImpl: TipsDataImpl

        var mDatabase: DatabaseReference? = null

        var listTestQuestions = listOf<TestEntity>()
        var _isLoadingQuestions = MutableLiveData<Boolean>(true)

        var listTipsOnline = ArrayList<TipsEntity>()
        var listTipsOffline = ArrayList<TipsEntity>()
        var listTipsBoth = ArrayList<TipsEntity>()
        lateinit var sqlDelightDB: WeLearnDatabase
    }

    override fun onCreate() {
        super.onCreate()

        mDatabase = FirebaseDatabase.getInstance().reference
        driver = AndroidSqliteDriver(WeLearnDatabase.Schema, this, "welearn.db")
        sqlDelightDB = WeLearnDatabase(driver!!)

        testImpl = TestDataSourceImpl(sqlDelightDB)
        dbVersionImpl = DBVersionDataSourceImpl(sqlDelightDB)
        tipsImpl = TipsDataImpl(sqlDelightDB)

        getFirebaseDBVersion()

    }

     suspend fun getLocalDBVersion() {
        dbVersionImpl?.getLocalDBVersion()?.collect(FlowCollector {
             if (it.isNotEmpty()) {
                testVersionLocalDB = it[0].testVersion?.toInt() ?: 0
                tipsVersionLocalDB = it[0].tipsVersion?.toInt() ?: 0
                videoListVersionLocalDB = it[0].videoVersion?.toInt() ?: 0

            }else{
                dbVersionImpl?.setLocalDBVersion(0,0,0)
                getLocalDBVersion()
            }
        })
    }

    private fun getFirebaseDBVersion() {
        mDatabase?.child(Constants.FIREBASE_DB_VERSION)?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    testVersionFirebase = snapshot.child("test").value.toString().toInt()
                    tipsVersionFirebase = snapshot.child("tips").value.toString().toInt()
                    videoListVersionFirebase = snapshot.child("video").value.toString().toInt()

                    CoroutineScope(Dispatchers.Main).launch {
                        getLocalDBVersion()
                    }
                    //                    if (dbVersionLocal != dbVersionFirebase) {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            getTestQuestionsFromFirebase()
//                            getVideoListFromFirebase()
//                            getTipsFromFirebase()
//                        }
//                    } else {
//                        /**set value from local db*/
//                        listTestQuestions = testImpl?.getAllQuestions() ?: listOf()
//                        videoList = videoListImpl?.getVideoList() ?: listOf()
//                        _isLoadingQuestions.value = false
//                        getTipsFromLocalDB()
//                    }
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

    private suspend fun getTipsFromFirebase() {
        mDatabase?.child(Constants.TIPS)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    snapshot.children.forEach {

                        when (it.key.toString()) {
                            TIPS_ONLINE -> {
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
                            TIPS_OFFLINE -> {
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
                            TIPS_BOTH -> {
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
                    getTipsFromLocalDB()

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

    private fun getTipsFromLocalDB() {
        listTipsOnline.addAll(tipsImpl.getTipsByType(TIPS_ONLINE))
        listTipsOffline.addAll(tipsImpl.getTipsByType(TIPS_OFFLINE))
        listTipsBoth.addAll(tipsImpl.getTipsByType(TIPS_BOTH))
    }


}