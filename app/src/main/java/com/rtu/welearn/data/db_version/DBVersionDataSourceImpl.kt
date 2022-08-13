package com.rtu.welearn.data.db_version

import com.rtu.welearn.WeLearnDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import welearndb.DbVersion

class DBVersionDataSourceImpl(db: WeLearnDatabase) : DBVersionDataSource {

    private val queries = db.dbVersionQueries

    override fun getLocalDBVersion(): Flow<List<DbVersion>> {
         return queries.getDBVersion().asFlow().mapToList()
    }

    override suspend fun setLocalDBVersion(version: Long?) {
        queries.setdbVersion(version)
    }
}