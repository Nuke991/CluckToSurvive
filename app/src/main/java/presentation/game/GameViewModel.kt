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


data class Platform(
    val coordinateX: Float,
    val coordinateY: Float,
    val width: Float,
    val height: Float
)



data class GameUiState(
    val characterX: Float = 120f,
    val characterY: Float = 60f,
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform> = listOf(
        Platform(40f, 300f, 120f, 30f),
        Platform(150f, 500f, 120f, 30f),
        Platform(120f, 700f , 120f,30f)
    )
)


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private var gameJob: Job? = null
    private val gravity = 0.08f
    private val jumpImpulse = -0.25f

    init { startGame() }

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
        val newY = state.characterY + newVelY
        var finalVelY = newVelY
        var collided = false

        if (newVelY > 0) {
            state.platforms.forEach { p ->
                if (newY + 100f >= p.coordinateX && state.characterY + 100f <= p.coordinateY &&
                    state.characterX in (p.coordinateX - 50f..p.coordinateX + p.width)) {
                    collided = true
                    finalVelY = jumpImpulse
                }
            }
        }

        val gameOver = newY > 2000f
        state.copy(
            characterY = if (collided) state.characterY else newY,
            velocityY = finalVelY,
            isGameOver = gameOver,
            score = if (!gameOver) state.score + 1 else state.score
        )
    }

    fun onPauseClick() = _uiState.update { it.copy(isPaused = !it.isPaused) }
    fun onRestartClick() { _uiState.value = GameUiState(); startGame() }
}

