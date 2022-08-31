package com.rtu.welearn.data.tips

import kotlinx.coroutines.flow.Flow
import welearndb.TipsEntity

interface TipsDataSource {
    suspend fun getTipsByType(type:String): Flow<List<TipsEntity>>
    suspend fun insertTip(id:Long?,type:String,tip:String)
}