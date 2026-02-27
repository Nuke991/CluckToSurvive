package com.mk.clucktosurvive.di


import com.mk.clucktosurvive.domain.model.repository.RecordRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RecordRepository(get ()) }
}

