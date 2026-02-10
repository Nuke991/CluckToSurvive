package presentation.records

import androidx.lifecycle.ViewModel
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

class RecordsScreenViewModel(): ViewModel(
){
    private val _uiState = MutableStateFlow(RecordsUiState())
    val uiState: StateFlow<RecordsUiState> = _uiState.asStateFlow()



    init {
        val records = listOf(
            Record("20.10", 1000f),
            Record("25.10", 1100f),
            Record("26.10", 1200f)
        )
        _uiState.value = RecordsUiState(records)
    }



}
