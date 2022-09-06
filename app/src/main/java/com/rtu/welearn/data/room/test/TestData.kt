package com.rtu.welearn.data.room.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TestData(

    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "points_1") val Points1: String,
    @ColumnInfo(name = "points_2") val Points2: String,
    @ColumnInfo(name = "points_3") val Points3: String,
    @ColumnInfo(name = "points_4") val Points4: String,
    @ColumnInfo(name = "points_5") val Points5: String,
    @ColumnInfo(name = "question") val Question: String,
    @ColumnInfo(name = "answer1") val Answer1: String,
    @ColumnInfo(name = "answer2") val Answer2: String,
    @ColumnInfo(name = "answer3") val Answer3: String,
    @ColumnInfo(name = "answer4") val Answer4: String,
    @ColumnInfo(name = "answer5") val Answer5: String
)
