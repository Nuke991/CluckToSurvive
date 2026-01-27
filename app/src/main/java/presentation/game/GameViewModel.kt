package presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class Character(
    val x: Float = 200f,
    val y: Float = 60f,
    val width: Float = 42f,
    val height: Float = 70f,
    val merge: Float = 20f

)

data class Platform(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)


data class GameUiState(
    val character: Character = Character(),
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform> = listOf(
        Platform(45f, 300f, 83f, 30f),
        Platform(150f, 500f, 83f, 30f),
        Platform(140f, 550f, 83f, 30f)
    )
)


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private var gameJob: Job? = null
    private val gravity = 0.8f
    private val jumpImpulse = -25f

    init {
        startGame()
    }

    private fun startGame() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (isActive) {
                if (!_uiState.value.isPaused && !_uiState.value.isGameOver) updatePhysics()
                delay(16)
            }
        }
    }

    private fun updatePhysics() = _uiState.update { state ->
        val newVelY = state.velocityY + gravity
        val newY = state.character.y + newVelY
        var finalVelY = newVelY
        var collided = false



        if (newVelY > 0) {

            state.platforms.forEach { p ->

                val isxcoordinatecorrect: Boolean = (state.character.x + state.character.width) in p.x..(p.x + p.width) || (p.x + p.width) in state.character.x..(state.character.x + state.character.width)


                if (
                    newY + state.character.height - state.character.merge >= p.y && state.character.y + state.character.height<= p.y + p.height  && isxcoordinatecorrect
                ) {
                    collided = true
                    finalVelY = jumpImpulse
                }


            }
        }

        val gameOver = newY > 2000f
        state.copy(
            character = Character(state.character.x, if (collided) state.character.y else newY),
            velocityY = finalVelY,
            isGameOver = gameOver,
            score = if (!gameOver) state.score + 1 else state.score
        )
    }
    fun onDrag(deltaX: Float) {
        _uiState.update { state ->
            val newX = (state.character.x + deltaX).coerceIn(0f, 1000f)
            state.copy(character = state.character.copy(x = newX))
        }
    }
    fun onPauseClick() = _uiState.update { it.copy(isPaused = !it.isPaused) }
    fun onRestartClick() {
        _uiState.value = GameUiState(); startGame()
    }
}

