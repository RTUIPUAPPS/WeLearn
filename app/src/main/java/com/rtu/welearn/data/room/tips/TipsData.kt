package com.rtu.welearn.data.room.tips

import androidx.room.*


@Entity
data class TipsData(


    @PrimaryKey (autoGenerate = true) val id :Int?,
    @ColumnInfo(name = "tip") val tip: String,
    @ColumnInfo(name = "type")val type: String
)

/* id INTEGER PRIMARY KEY NOT NULL,
    Points1 TEXT,
    Points2 TEXT,
    Points3 TEXT,
    Points4 TEXT,
    Points5 TEXT,
    Question TEXT,
    Answer1 TEXT,
    Answer2 TEXT,
    Answer3 TEXT,
    Answer4 TEXT,
    Answer5 TEXT*/
