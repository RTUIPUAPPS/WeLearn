package com.rtu.welearn.data.room.tips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TipsDao {

    @Query("SELECT * FROM TipsData WHERE type=:type")
    suspend fun getAllTips(type: String): List<TipsData?>?

    @Query("SELECT * FROM TipsData")
    fun getTips(): List<TipsData?>?

    @Insert
    suspend fun insertAllTips(vararg users: TipsData)

    @Query("DELETE  FROM TipsData")
    fun deleteTips()
}