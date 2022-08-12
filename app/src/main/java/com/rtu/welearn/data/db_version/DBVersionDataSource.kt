package com.rtu.welearn.data.db_version

interface DBVersionDataSource {
    fun getLocalDBVersion()
    fun setLocalDBVersion(version: Int)
}