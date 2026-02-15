package com.mk.clucktosurvive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val date: String,
    val score : Int = 0


) {


}