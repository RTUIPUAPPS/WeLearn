package com.rtu.welearn.data.tips

import welearndb.TipsEntity

interface TipsDataSource {
    fun getTipsByType(type:String):List<TipsEntity>
    suspend fun insertTip(id:Long?,type:String,tip:String)
}