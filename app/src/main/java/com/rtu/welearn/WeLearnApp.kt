package com.rtu.welearn

import android.app.Application
import com.google.firebase.database.*
import com.rtu.welearn.data.room.AppDatabase
import com.rtu.welearn.data.room.db_version.DBVersionData
import com.rtu.welearn.data.room.test.TestData
import com.rtu.welearn.utils.Constants
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeLearnApp : Application() {
    companion object {
        var driver: SqlDriver? = null
        var mDatabase: DatabaseReference? = null

        var testVersionFirebase = 0
        var tipsVersionFirebase = 0
        var videoListVersionFirebase = 0
        lateinit var roomDB: AppDatabase
        lateinit var dbVersionData: DBVersionData

        var listAllQuestions = ArrayList<TestData>()
    }

    override fun onCreate() {
        super.onCreate()

        roomDB = AppDatabase.getDatabase(this)
        mDatabase = FirebaseDatabase.getInstance().reference
        getFirebaseDBVersion()
    }

    suspend fun getLocalDBVersion() {
        val listDBVersions = roomDB.dbVersionDao().getVersion()
        if (listDBVersions?.isNullOrEmpty() == true) {
            dbVersionData = DBVersionData(
                0, 0, 0, 0
            )
            roomDB.dbVersionDao().insertVersion(
                dbVersionData
            )
        } else {
            dbVersionData = listDBVersions[0]!!
        }
//        dbVersionImpl?.getLocalDBVersion()?.collect(FlowCollector {
//            if (it.isNotEmpty()) {
//                testVersionLocalDB = it[0].testVersion?.toInt() ?: 0
//                tipsVersionLocalDB = it[0].tipsVersion?.toInt() ?: 0
//                videoListVersionLocalDB = it[0].videoVersion?.toInt() ?: 0
//
//            } else {
//                dbVersionImpl?.setLocalDBVersion(0, 0, 0)
//                getLocalDBVersion()
//            }
//        })
    }

    private fun getFirebaseDBVersion() {
        mDatabase?.child(Constants.FIREBASE_DB_VERSION)?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    testVersionFirebase = snapshot.child("test").value.toString().toInt()
                    tipsVersionFirebase = snapshot.child("tips").value.toString().toInt()
                    videoListVersionFirebase = snapshot.child("video").value.toString().toInt()

                    CoroutineScope(Dispatchers.IO).launch {
                        getLocalDBVersion()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}