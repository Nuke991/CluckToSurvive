package com.mk.clucktosurvive.data.repository

import com.mk.clucktosurvive.domain.model.ScoreRecord
import com.mk.clucktosurvive.data.local.entity.RecordEntity
import com.mk.clucktosurvive.data.local.dao.IRecordDao
import kotlinx.coroutines.flow.Flow

class RecordRepository(private val recordDao: IRecordDao) {


    val records: Flow<List<RecordEntity>> = recordDao.getRecords()



    suspend fun addRecord(record: ScoreRecord){
        val recordEntity = RecordEntity(
            date = record.date,
            score = record.score
        )
        recordDao.insertRecord(recordEntity)
        recordDao.deleteExcessiveRecords(4)
    }


}