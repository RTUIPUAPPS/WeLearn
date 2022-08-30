package com.rtu.welearn.data.db_version

import kotlinx.coroutines.flow.Flow
import welearndb.DbVersion

interface DBVersionDataSource {
    fun getLocalDBVersion(): Flow<List<DbVersion>>?
    suspend fun setLocalDBVersion(
        testVersion: Long,
        tipsVersion: Long,
        videoVersion: Long
    )

    suspend fun updateVideoVersion(version: Long)

    suspend fun updateTestQuestionsVersion(version: Long)
}