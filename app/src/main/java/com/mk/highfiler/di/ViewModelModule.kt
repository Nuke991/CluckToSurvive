package com.mk.highfiler.di

import com.mk.highfiler.presentation.game.GameViewModel
import com.mk.highfiler.presentation.records.RecordsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module{
    viewModel { GameViewModel(get()) }
    viewModel { RecordsScreenViewModel(get()) }
}