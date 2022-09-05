package com.rtu.welearn.data.room.tips

import androidx.room.*


@Entity
data class TipsData(
    /* val Points1 :String,
     val Points2 :String,
     val Points3 :String,
     val Points4 :String,
     val Points5 :String,
     val Question :String,
     val Answer1 :String,
     val Answer2 :String,
     val Answer3 :String,
     val Answer4 :String,
     val Answer5 :String*/

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
