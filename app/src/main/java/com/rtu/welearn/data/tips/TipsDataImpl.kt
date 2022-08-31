package com.rtu.welearn.data.tips

import com.rtu.welearn.WeLearnDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import welearndb.TipsEntity

class TipsDataImpl(db: WeLearnDatabase) : TipsDataSource {
    private val queries = db.tipsEntityQueries
    override suspend fun getTipsByType(type: String): Flow<List<TipsEntity>> {
        return queries.getTipsByType(type).asFlow().mapToList()
    }

    override suspend fun insertTip(id: Long?, type: String, tip: String) {
        queries.insertTip(id, type, tip)
    }

}