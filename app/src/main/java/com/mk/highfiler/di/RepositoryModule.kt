package com.mk.highfiler.di


import com.mk.highfiler.domain.model.repository.RecordRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RecordRepository(get ()) }
}

