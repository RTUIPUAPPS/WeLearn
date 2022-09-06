package com.rtu.welearn.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rtu.welearn.R
import com.rtu.welearn.data.room.db_version.DBVersionDao
import com.rtu.welearn.data.room.db_version.DBVersionData
import com.rtu.welearn.data.room.test.TestDao
import com.rtu.welearn.data.room.test.TestData
import com.rtu.welearn.data.room.tips.TipsDao
import com.rtu.welearn.data.room.tips.TipsData
import com.rtu.welearn.data.room.videolist.VideoListDao
import com.rtu.welearn.data.room.videolist.VideoListData

@Database(
    entities = [TipsData::class, DBVersionData::class, TestData::class, VideoListData::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        fun getDatabase(context: Context): AppDatabase {
            var database: AppDatabase? = null
            val dbName = context.getString(R.string.app_name) + ".db"
            if (database == null) {
                synchronized(AppDatabase::class) {
                    if (database == null) {
                        database = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, dbName
                        ).allowMainThreadQueries().addCallback(object : RoomDatabase.Callback() {

                        }).build()
                    }
                }
            }
            return database!!
        }
    }

    abstract fun tipsDao(): TipsDao
    abstract fun dbVersionDao(): DBVersionDao
    abstract fun TestDao(): TestDao
    abstract fun VideoListDao(): VideoListDao
}