package com.rtu.welearn.data.video_list

import welearndb.VideoListEntity

interface VideoListDataSource {

    fun getVideoList():List<VideoListEntity>
    suspend fun insertVideoDetails(
        id :Long?,
        videoID: String,
        videoTitle: String,
        videoDescription: String
    )
    fun deleteVideoList()

}