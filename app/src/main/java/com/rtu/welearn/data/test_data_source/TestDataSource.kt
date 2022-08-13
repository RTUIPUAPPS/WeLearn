package com.rtu.welearn.data.test_data_source

import kotlinx.coroutines.flow.Flow
import welearndb.TestEntity

interface TestDataSource {
    fun getAllQuestions(): List<TestEntity>
    fun deleteAllQuestions()
    suspend fun insertQuestion(
        id: Long?,
        Point1: String,
        Point2: String,
        Point3: String,
        Point4: String,
        Point5: String,
        Question: String,
        Answer1: String,
        Answer2: String,
        Answer3: String,
        Answer4: String,
        Answer5: String
    )
}