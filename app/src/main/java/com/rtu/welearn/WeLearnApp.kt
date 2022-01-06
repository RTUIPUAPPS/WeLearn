package com.rtu.welearn

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WeLearnApp:Application() {
    companion object{
        var mDatabase : DatabaseReference?=null
    }

    override fun onCreate() {
        super.onCreate()
        mDatabase = FirebaseDatabase.getInstance().reference;
    }
}