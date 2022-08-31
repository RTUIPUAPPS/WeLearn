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
        var dbVersionImpl: DBVersionDataSourceImpl? = null
        var mDatabase: DatabaseReference? = null
        lateinit var sqlDelightDB: WeLearnDatabase

        var testVersionLocalDB = 0
        var tipsVersionLocalDB = 0
        var videoListVersionLocalDB = 0

        var testVersionFirebase = 0
        var tipsVersionFirebase = 0
        var videoListVersionFirebase = 0
    }

    override fun onCreate() {
        super.onCreate()

        mDatabase = FirebaseDatabase.getInstance().reference
        driver = AndroidSqliteDriver(WeLearnDatabase.Schema, this, "welearn.db")
        sqlDelightDB = WeLearnDatabase(driver!!)
        dbVersionImpl = DBVersionDataSourceImpl(sqlDelightDB)
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

                    CoroutineScope(Dispatchers.IO).launch {
                        getLocalDBVersion()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}