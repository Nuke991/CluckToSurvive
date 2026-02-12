package com.mk.clucktosurvive.presentation.records

import androidx.lifecycle.ViewModel
import com.mk.clucktosurvive.data.repository.RecordRepository
import com.mk.clucktosurvive.domain.model.ScoreRecord

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow




data class RecordsUiState(
    val records: MutableList<ScoreRecord> = mutableListOf()


)

class RecordsScreenViewModel(var  repository: RecordRepository): ViewModel(
){

    private val _uiState = MutableStateFlow(RecordsUiState())
    val uiState: StateFlow<RecordsUiState> = _uiState.asStateFlow()


    init {
        loadRecords()
    }

    private fun loadRecords() {
        _uiState.value = RecordsUiState(repository.records)
    }


}
