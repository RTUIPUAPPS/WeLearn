package com.rtu.welearn.data.db_version

import com.rtu.welearn.WeLearnDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import welearndb.DbVersion

class DBVersionDataSourceImpl(db: WeLearnDatabase) : DBVersionDataSource {

    private val queries = db.dbVersionQueries

    override fun getLocalDBVersion(): Flow<List<DbVersion>> {
        return queries.getDBVersion().asFlow().mapToList()
    }

    override suspend fun setLocalDBVersion(
        testVersion: Long,
        tipsVersion: Long,
        videoVersion: Long
    ) {

        queries.setdbVersion(testVersion, tipsVersion, videoVersion)
    }

    override suspend fun updateVideoVersion(version: Long) {
        queries.updateVideoVerison(version)
    }

    override suspend fun updateTestQuestionsVersion(version: Long) {
        queries.updateTestVerison(version)
    }
}