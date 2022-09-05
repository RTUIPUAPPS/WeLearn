package com.rtu.welearn.data.room.db_version

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rtu.welearn.data.room.tips.TipsData

@Dao
interface DBVersionDao {
    @Query("SELECT * FROM DBVersionData")
    suspend fun getVersion(): List<DBVersionData?>?

    @Insert
    suspend fun insertVersion(vararg users: DBVersionData)

    @Update
    suspend fun updateVersion(vararg users: DBVersionData)

    @Query("DELETE  FROM DBVersionData")
    fun deleteVersions()
}