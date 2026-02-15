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

    @Query("Select * From record_table Order By date asc")
    fun getRecords(): Flow<List<RecordEntity>>

    @Query("Delete From record_table")
    suspend fun clearallrecords()

}