package di

import org.koin.androidx.viewmodel.dsl.viewModel
import presentation.game.GameViewModel
import presentation.records.RecordsScreenViewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { GameViewModel(get()) }
    viewModel { RecordsScreenViewModel(get()) }
}