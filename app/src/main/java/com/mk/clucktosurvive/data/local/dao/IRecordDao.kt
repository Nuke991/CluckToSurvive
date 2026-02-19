package com.mk.clucktosurvive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mk.clucktosurvive.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface IRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: RecordEntity)

    @Query("SELECT* FROM record_table ORDER BY score DESC")
    fun getRecords(): Flow<List<RecordEntity>>

    @Query("DELETE FROM record_table WHERE id NOT IN (SELECT id FROM record_table ORDER BY score DESC LIMIT :count)")
    suspend fun deleteExcessiveRecords(count: Int)


}