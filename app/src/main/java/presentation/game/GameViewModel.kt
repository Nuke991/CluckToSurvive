package presentation.game

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.clucktosurvive.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class Character(
    var xDp: Float = 0f,
    var yDp: Float = 0f,
    val widthDp: Float = 42f,
    val heightDp: Float = 70f,
    val mergeDp: Float = 20f


)


enum class PlatformType(val resourceId: Int) {
    SMALL(R.drawable.platform_small),
    BIG(R.drawable.platform_big),

}



enum class GameScreen(val value: Int) {
    MOVETOP(1),
    MOVEDOWN(-1),
    FIXED(0);

}

data class Platform(
    val x: Float,
    var y: Float,
  //  val platformType: PlatformType,
    val platformBitmap: ImageBitmap

)


data class GameUiState(
    val gameScreen: GameScreen = GameScreen.FIXED,
    val character: Character = Character(),
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform>  = listOf()


)


class GameViewModel : ViewModel() {
    private var screenWidthDp: Float = 0f
    private var screenHeightDp: Float = 2000f
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private var gameJob: Job? = null
    private val gravity = 0.8f
    private val jumpImpulse = -20f
    private val topborderYDp = 400f
    private val downborderY = 800f
    private var isGameLaunched = false



    init {

       // resetGame()
       // startGame()
    }

    fun resetGame(charWidthPx: Float, charHeightPx: Float, context: Context, density: Float
    ) {
        if (isGameLaunched) return
        val characterWidth = charWidthPx/density
        val characterHeight= charHeightPx/density

        val centerX = (screenWidthDp / 2) - (characterWidth / 2)
        val centerY = screenHeightDp * 0.2f

        _uiState.update{ it.copy(
            character = Character(
                centerX,
                centerY,
                widthDp = characterWidth,
                heightDp = characterHeight,
            ),
            isGameOver = false,


            platforms = listOf(
                Platform(170f, 600f,  BitmapFactory.decodeResource(context.resources, PlatformType.BIG.resourceId).asImageBitmap()),
                Platform(270f, 500f, BitmapFactory.decodeResource(context.resources, PlatformType.SMALL.resourceId).asImageBitmap()),
                Platform(45f, 400f, BitmapFactory.decodeResource(context.resources, PlatformType.BIG.resourceId).asImageBitmap()),
                Platform(100f, 300f, BitmapFactory.decodeResource(context.resources, PlatformType.SMALL.resourceId).asImageBitmap())))
        }
        isGameLaunched = true
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
        val newY = state.character.yDp + newVelocityY
        var finalVelocityY = newVelocityY
        var collided = false
        val platformShift = if (newY <= topborderYDp && newVelocityY < 0) topborderYDp - newY else 0f


        //val scrollThreshold = 400f
        // val scrollOffset = if (newY < scrollThreshold) scrollThreshold - newY else 0f


        if (newVelocityY > 0) {

            state.platforms.forEach { p ->

                val isxcoordinatecorrect: Boolean =
                    (state.character.xDp + state.character.widthDp) in p.x..(p.x + p.platformBitmap.width) || (p.x + p.platformBitmap.width) in state.character.xDp..(state.character.xDp + state.character.widthDp)


                if (
                    newY + state.character.heightDp - state.character.mergeDp >= p.y && state.character.yDp + state.character.heightDp <= p.y + p.platformBitmap.height && isxcoordinatecorrect
                ) {
                    collided = true
                    finalVelocityY = jumpImpulse
                }


            }
        }


        val gameScreenState = if (newY <= topborderYDp) {
            GameScreen.MOVEDOWN
        } else if (newY >= downborderY) {
            GameScreen.MOVETOP
        } else GameScreen.FIXED


        val finalY =
            if (gameScreenState == GameScreen.MOVEDOWN && newVelocityY < 0) {
                topborderYDp
            } else {
                (if (collided) state.character.yDp else newY)
            }





       // val newCharY = if (newY <= topborderYDp && newVelocityY < 0) topborderYDp else if (collided) state.character.yDp else newY;

        val newCharY =  if (collided) state.character.yDp else newY;


        state.copy(
             character =  state.character.copy( yDp= newCharY),

            velocityY = finalVelocityY,
            //score = state.score + scrollOffset.toInt(),
            isGameOver = finalY > 2000f,

           /* platforms = state.platforms.map { p ->
                val moveplatformY = p.y + platformShift
                if (moveplatformY > screenHeightDp) {
                    p.copy(
                        y = 150f,
                        x = (0..screenWidthDp.toInt()-p.platformBitmap.width.toInt()).random().toFloat())
                } else
                    p.copy(y = moveplatformY)

            }
*/

        )
    }

    fun onDrag(deltaX: Float) {
        _uiState.update { state ->
            val newX =
                (state.character.xDp + deltaX).coerceIn(0f, screenWidthDp - state.character.widthDp)
            state.copy(character = state.character.copy(xDp = newX))
        }
    }

    fun onPauseClick() = _uiState.update { it.copy(isPaused = !it.isPaused) }

    /*fun onRestartClick() {
        _uiState.value = GameUiState(); startGame()

    }*/

    fun updateScreenSize(widthPx: Float, heightPx: Float, density: Float) {
        screenWidthDp = widthPx / density
        screenHeightDp = heightPx / density

    }


}

