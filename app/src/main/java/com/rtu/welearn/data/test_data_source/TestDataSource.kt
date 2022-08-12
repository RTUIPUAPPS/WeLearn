package com.rtu.welearn.data.test_data_source

import kotlinx.coroutines.flow.Flow
import welearndb.TestEntity

interface TestDataSource {
    fun getAllQuestions(): Flow<List<TestEntity>>
    suspend fun insertQuestion(
        question: String,
        answer1: String,
        answer2: String,
        answer3: String,
        answer4: String,
        answer5: String,
        point1: String,
        point2: String,
        point3: String,
        point4: String,
        point5: String,
        id:Long?=null
    )
}