package com.mk.clucktosurvive.di

import com.mk.clucktosurvive.presentation.game.GameViewModel
import com.mk.clucktosurvive.presentation.records.RecordsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val viewModelModule = module{
    viewModel { GameViewModel(get()) }
    viewModel { RecordsScreenViewModel(get()) }
}