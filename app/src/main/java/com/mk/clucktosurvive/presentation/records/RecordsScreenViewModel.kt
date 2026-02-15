package com.mk.clucktosurvive.presentation.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.clucktosurvive.data.repository.RecordRepository
import com.mk.clucktosurvive.domain.model.ScoreRecord

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class RecordsUiState(
    val records: List<ScoreRecord> = emptyList()
)

class RecordsScreenViewModel(var repository: RecordRepository) : ViewModel(
) {

    private val _uiState = MutableStateFlow(RecordsUiState())
    val uiState: StateFlow<RecordsUiState> = _uiState.asStateFlow()


    init {
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {

            repository.records.collect { entities ->
                val scoreRecords = entities.map { entity ->
                    ScoreRecord(
                        entity.date,
                        entity.score
                    )
                }
                _uiState.value = RecordsUiState(scoreRecords)
            }
        }
    }


}
