package com.rtu.welearn.data.room.videolist

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Keep
data class VideoListData (
    @PrimaryKey(autoGenerate = true) val id :Int?,
    @ColumnInfo(name = "video_id") val videoID: String,
    @ColumnInfo(name = "video_title")val videoTitle: String,
    @ColumnInfo(name = "video_description")val videoDescription: String
)