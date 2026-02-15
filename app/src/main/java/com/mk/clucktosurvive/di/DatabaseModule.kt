package com.mk.clucktosurvive.di

import com.mk.clucktosurvive.data.local.entity.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDataBase.getInstance(androidContext()) }
    single { get<AppDataBase>().gameRecordDao() }
}