package com.rtu.welearn

import android.app.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.data.room.AppDatabase
import com.rtu.welearn.data.room.db_version.DBVersionData
import com.rtu.welearn.data.room.test.TestData
import com.rtu.welearn.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeLearnApp : Application() {
    companion object {
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
        if (listDBVersions?.isEmpty() == true) {
            dbVersionData = DBVersionData(
                0, 0, 0, 0
            )
            roomDB.dbVersionDao().insertVersion(
                dbVersionData
            )
        } else {
            dbVersionData = listDBVersions?.get(0)!!
        }
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