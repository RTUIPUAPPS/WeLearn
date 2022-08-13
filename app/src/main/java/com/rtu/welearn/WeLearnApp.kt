package com.rtu.welearn

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.rtu.welearn.data.db_version.DBVersionDataSourceImpl
import com.rtu.welearn.data.test_data_source.TestDataSourceImpl
import com.rtu.welearn.ui.video_list.VideoDetails
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.VIDEO_DESCRIPTION
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_TITLE
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class WeLearnApp : Application() {
    companion object {
        var driver: SqlDriver? = null
        var dbVersionLocal = 0
        var dbVersionFirebase = 0

        //        var testQueries: TestEntityQueries? = null
        var testImpl: TestDataSourceImpl? = null

        //        var dbVersionQueries: DbVersionQueries? = null
        var dbVersionImpl: DBVersionDataSourceImpl? = null

        var mDatabase: DatabaseReference? = null
        private var mCloudEndPointVideoList: DatabaseReference? = null
        var mCloudEndPointTestQuestions: DatabaseReference? = null
        var mCloudEndPointDBVersio: DatabaseReference? = null
        var VideoList = arrayListOf<VideoDetails>()
        var _isLoadingQuestions = MutableLiveData<Boolean>(false)
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Main).launch {
            getLocalDBVersion()
        }
        mDatabase = FirebaseDatabase.getInstance().reference
        driver = AndroidSqliteDriver(WeLearnDatabase.Schema, this, "welearn.db")
        val sqlDelightDB = WeLearnDatabase(driver!!)

        testImpl = TestDataSourceImpl(sqlDelightDB)
        dbVersionImpl = DBVersionDataSourceImpl(sqlDelightDB)



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

    suspend fun getLocalDBVersion() {
        dbVersionImpl?.getLocalDBVersion()?.collect(FlowCollector {
            if(it.isNotEmpty()){
                dbVersionLocal = it[0].version?.toInt() ?: 0
            }
            getFirebaseDBVersion()
        })
    }

    fun getFirebaseDBVersion(){
        mCloudEndPointDBVersio = mDatabase?.child(Constants.FIREBASE_DB_VERSION)
        mCloudEndPointDBVersio?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dbVersionFirebase = snapshot.value.toString().toInt()
                if (dbVersionLocal != dbVersionFirebase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        getTestQuestionsFromFirebase()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private suspend fun getTestQuestionsFromFirebase() {
        mCloudEndPointTestQuestions = mDatabase?.child(Constants.TEST)
        mCloudEndPointTestQuestions?.addListenerForSingleValueEvent(object :
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
                    dbVersionImpl?.setLocalDBVersion(dbVersionFirebase.toLong())
                    _isLoadingQuestions.value = false
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}