package presentation.records

import androidx.lifecycle.ViewModel
import data.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import presentation.game.GameUiState

data class Record(
    var date: String,
    val score: Float

)


data class RecordsUiState(
    val records: List<Record> = listOf()


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
