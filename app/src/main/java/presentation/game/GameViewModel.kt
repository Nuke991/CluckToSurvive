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
    var x: Float = 0f,
    var y: Float = 0f,
    val width: Float = 42f,
    val height: Float = 70f,
    val merge: Float = 20f


)

enum class GameScreen(val value: Int) {
    MOVETOP(1),
    MOVEDOWN(-1),
    FIXED(0);

}

data class Platform(
    val x: Float,
    var y: Float,
    val width: Float,
    val height: Float
)


data class GameUiState(
    val gameScreen: GameScreen = GameScreen.FIXED,
    val character: Character = Character(),
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform> = listOf(
        Platform(170f, 500f, 83f, 30f),
        Platform(45f, 400f, 83f, 30f),
        Platform(150f, 600f, 83f, 30f),
        Platform(100f, 300f, 83f, 30f)
    )
)


class GameViewModel : ViewModel() {
    private var screenWidth: Float = 0f
    private var screenHeight: Float = 2000f
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private var gameJob: Job? = null
    private val gravity = 0.8f
    private val jumpImpulse = -20f
    private val topborderY = 400f
    private val downborderY = 800f

    init {

       // resetGame()
       // startGame()
    }

    fun resetGame(widthPx: Int, heightPx: Int, density: Float) {
        val charWidthDp = widthPx / density
        val charHeightDp = heightPx / density



        val centerX = (screenWidth / 2) - (charWidthDp / 2)
        val centerY = screenHeight * 0.2f

        _uiState.update{ it.copy(
            character = Character(
                centerX ,
                centerY.toFloat() ,
                width = charWidthDp,
                height = charHeightDp,
            ),
            isGameOver = false,
            platforms = listOf(


                Platform(170f, 600f, 83f, 30f),
                Platform(270f, 500f, 83f, 30f),
                Platform(45f, 400f, 83f, 30f),
                Platform(100f, 300f, 83f, 30f)))
        }

    }

    fun startGame() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (isActive) {
                if (!_uiState.value.isPaused && !_uiState.value.isGameOver) updatePhysics()
                delay(16)
            }
        }
    }

    private fun updatePhysics() = _uiState.update { state ->
        val newVelocityY = state.velocityY + gravity
        val newY = state.character.y + newVelocityY
        var finalVelocityY = newVelocityY
        var collided = false
        val platformShift = if (newY <= topborderY && newVelocityY < 0) topborderY - newY else 0f


        //val scrollThreshold = 400f
        // val scrollOffset = if (newY < scrollThreshold) scrollThreshold - newY else 0f


        if (newVelocityY > 0) {

            state.platforms.forEach { p ->

                val isxcoordinatecorrect: Boolean =
                    (state.character.x + state.character.width) in p.x..(p.x + p.width) || (p.x + p.width) in state.character.x..(state.character.x + state.character.width)


                if (
                    newY + state.character.height - state.character.merge >= p.y && state.character.y + state.character.height <= p.y + p.height && isxcoordinatecorrect
                ) {
                    collided = true
                    finalVelocityY = jumpImpulse
                }


            }
        }


        val gameScreenState = if (newY <= topborderY) {
            GameScreen.MOVEDOWN
        } else if (newY >= downborderY) {
            GameScreen.MOVETOP
        } else GameScreen.FIXED


        val finalY =
            if (gameScreenState == GameScreen.MOVEDOWN && newVelocityY < 0) {
                topborderY
            } else {
                (if (collided) state.character.y else newY)
            }






        state.copy(
            character = Character(
                state.character.x,
                if (newY <= topborderY && newVelocityY < 0) topborderY else if (collided) state.character.y else newY,
                state.character.width,
                state.character.height
            ),
            velocityY = finalVelocityY,
            //score = state.score + scrollOffset.toInt(),
            isGameOver = finalY > 2000f,
            platforms = state.platforms.map { p ->
                val moveplatformY = p.y + platformShift
                if (moveplatformY > screenHeight) {
                    p.copy(y = 150f, x = (0..screenWidth.toInt()-p.width.toInt()).random().toFloat())


                } else
                    p.copy(y = moveplatformY)

            }

        )
    }

    fun onDrag(deltaX: Float) {
        _uiState.update { state ->
            val newX =
                (state.character.x + deltaX).coerceIn(0f, screenWidth - state.character.width)
            state.copy(character = state.character.copy(x = newX))
        }
    }

    fun onPauseClick() = _uiState.update { it.copy(isPaused = !it.isPaused) }

    /*fun onRestartClick() {
        _uiState.value = GameUiState(); startGame()

    }*/

    fun updateScreenSize(widthPx: Float, heightPx: Float, density: Float) {
        screenWidth = widthPx / density
        screenHeight = heightPx / density
    }


}

