package com.rtu.welearn.data.room.videolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoListDao {

    @Insert
    suspend fun insertVideoData(videoData: VideoListData)

    @Query("SELECT * FROM VideoListData")
    fun getVideoList(): Flow<List<VideoListData>>

    @Query("DELETE FROM VideoListData")
    suspend fun deleteVideoList()
}