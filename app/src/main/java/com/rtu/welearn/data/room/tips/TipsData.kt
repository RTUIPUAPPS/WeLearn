package com.rtu.welearn.data.room.tips

import androidx.annotation.Keep
import androidx.room.*

@Entity
@Keep
data class TipsData(

    @PrimaryKey (autoGenerate = true) val id :Int?,
    @ColumnInfo(name = "tip") val tip: String,
    @ColumnInfo(name = "type")val type: String
)