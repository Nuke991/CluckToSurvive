package com.mk.clucktosurvive.data.repository

import com.mk.clucktosurvive.domain.model.ScoreRecord


class RecordRepository {


    val records: MutableList<ScoreRecord> = mutableListOf(
    )

    fun addRecord(record: ScoreRecord){
        records.add(record);


    }


}