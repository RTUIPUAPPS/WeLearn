package com.rtu.welearn.data.room.test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Query("SELECT * FROM TestData")
    fun getTestData(): Flow<List<TestData>>

    @Query("SELECT * FROM TestData")
    suspend fun getTest(): List<TestData>

    @Insert
    suspend fun insertTestData(testData: TestData)

    @Query("DELETE FROM TestData")
    suspend fun deleteTestQuestions()
}