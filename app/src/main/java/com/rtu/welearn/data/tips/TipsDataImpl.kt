package com.rtu.welearn.data.tips

import com.rtu.welearn.WeLearnDatabase
import welearndb.TipsEntity

class TipsDataImpl(db: WeLearnDatabase) : TipsDataSource {
    private val queries = db.tipsEntityQueries
    override fun getTipsByType(type: String): List<TipsEntity> {
        return queries.getTipsByType(type).executeAsList()
    }

    override suspend fun insertTip(id: Long?, type: String, tip: String) {
        queries.insertTip(id, type, tip)
    }

}