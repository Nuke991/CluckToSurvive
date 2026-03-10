package com.mk.highfiler.di

import com.mk.highfiler.data.local.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDataBase.getInstance(androidContext()) }
    single { get<AppDataBase>().gameRecordDao() }
}