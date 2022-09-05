package com.rtu.welearn.data.room.db_version

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DBVersionData(
    @PrimaryKey(autoGenerate = true) val id :Int?,
    @ColumnInfo(name = "version_tips") var version_tips: Int,
    @ColumnInfo(name = "version_test")var version_test: Int,
    @ColumnInfo(name = "version_video")var version_video: Int
)
