package com.mk.clucktosurvive.data.repository

import com.mk.clucktosurvive.presentation.records.Record

class RecordRepository {


    val records: List<Record> = listOf(
        Record("20.10", 1000f),
        Record("25.10", 1100f),
        Record("26.10", 1200f)
    )



}