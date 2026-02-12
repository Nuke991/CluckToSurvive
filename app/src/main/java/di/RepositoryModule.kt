package di

import data.repository.RecordRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RecordRepository() }
}

