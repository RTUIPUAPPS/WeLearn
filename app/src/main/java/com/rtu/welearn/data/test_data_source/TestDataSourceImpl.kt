package com.rtu.welearn.data.test_data_source

import com.rtu.welearn.WeLearnDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import welearndb.TestEntity

class TestDataSourceImpl(db: WeLearnDatabase) : TestDataSource {

    private val queries = db.testEntityQueries

    override fun getAllQuestions(): Flow<List<TestEntity>> {
        return queries.getAllQuestions().asFlow().mapToList()
    }

    override suspend fun insertQuestion(
        question: String,

        point1: String,
        point2: String,
        point3: String,
        point4: String,
        point5: String,
        answer1: String,
        answer2: String,
        answer3: String,
        answer4: String,
        answer5: String,
        id: Long?
    ) {

        withContext(Dispatchers.IO){
            queries.insertQuestion(id,
                point1,
                point2,
                point3,
                point4,
                point5,
                question,
                answer1,
                answer2,
                answer3,
                answer4,
                answer5,


            )
        }
    }

}