package com.mk.highfiler.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mk.highfiler.data.local.dao.IRecordDao
import com.mk.highfiler.data.local.entity.RecordEntity

@Database(
    entities = [RecordEntity::class],
    version = 1,
    exportSchema = false
)


abstract class AppDataBase : RoomDatabase()  {

    abstract fun gameRecordDao(): IRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "clucktosurvive_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}