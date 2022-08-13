package com.rtu.welearn.data.db_version

import kotlinx.coroutines.flow.Flow
import welearndb.DbVersion

interface DBVersionDataSource {
    fun getLocalDBVersion(): Flow<List<DbVersion>>?
    suspend fun setLocalDBVersion(version: Long?)
}