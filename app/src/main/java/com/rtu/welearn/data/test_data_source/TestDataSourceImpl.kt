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

    override fun getAllQuestions(): List<TestEntity> {
        return queries.getAllQuestions().executeAsList()
    }

    override fun deleteAllQuestions() {
       queries.deleteQuestions()
    }

    override suspend fun insertQuestion(
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
    ) {

        withContext(Dispatchers.IO){
            queries.insertQuestion(
                id,
                Point1,
                Point2,
                Point3,
                Point4,
                Point5,
                Question,
                Answer1,
                Answer2,
                Answer3,
                Answer4,
                Answer5,
            )
        }
    }

}