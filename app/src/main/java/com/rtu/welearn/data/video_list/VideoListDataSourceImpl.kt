package com.rtu.welearn.data.video_list

import com.rtu.welearn.WeLearnDatabase
import welearndb.VideoListEntity

class VideoListDataSourceImpl(db: WeLearnDatabase):VideoListDataSource {

    private val queries = db.videoListEntityQueries
    override fun getVideoList(): List<VideoListEntity> {
        return queries.getVideoList().executeAsList()
    }

    override suspend fun insertVideoDetails(
        id: Long?,
        videoID: String,
        videoTitle: String,
        videoDescription: String
    ) {
        queries.insertvideoDetails(id,videoID,videoTitle,videoDescription)
    }

    override fun deleteVideoList() {
       queries.deleteVideos()
    }
}