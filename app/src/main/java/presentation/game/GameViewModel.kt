package presentation.game

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.remote.creation.random
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

data class Record(
    var date: String,
    val score: Float

)
data class Platform(

    var xDp: Float,
    var yDp: Float,
    //  val platformType: PlatformType,
    val platformBitmap: ImageBitmap,
    val density: Float

) {

    val widthDp: Float
        get() = platformBitmap.width / density

    val heightDp: Float
        get() = platformBitmap.height / density

}


data class GameUiState(
    val gameScreen: GameScreen = GameScreen.FIXED,
    val character: Character = Character(),
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform> = listOf(),
    val records: List<Record> = listOf()

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
    private val maxStepSize: Float = 200f;


    fun resetGame(
        charWidthPx: Float, charHeightPx: Float, context: Context, density: Float
    ) {
        if (isGameLaunched) return
        val characterWidth = charWidthPx / density
        val characterHeight = charHeightPx / density

        val screencenterdpX = screenWidthDp * 0.5f
        val screencenterdpY = screenHeightDp * 0.5f

        val plList = mutableListOf<Platform>()
        val recordslist = listOf(
            Record("20.10", 1000f),
            Record("25.10", 1100f),
            Record("26.10", 1200f)
        )
        var platformYdp: Float = 600f;
        var isFirst: Boolean = true


        do {

            val platformType: PlatformType =  PlatformType.entries[(0..1).random()];
            val bitmap = BitmapFactory.decodeResource(context.resources, platformType.resourceId)
                .asImageBitmap()
            val platformXdp: Float = if(isFirst) { screencenterdpX - (bitmap.width/density)/2} else {(0..screenWidthDp.toInt()-(bitmap.width/density).toInt()).random().toFloat()};

            val newPlatform =  Platform(
                platformXdp,
                platformYdp,
                bitmap,
                density
            )
            plList.add(newPlatform);

            platformYdp -= ((maxStepSize/2).toInt()..maxStepSize.toInt()).random().toFloat();
            isFirst = false
        } while (platformYdp >= -2*maxStepSize)



        _uiState.update {
            it.copy(
                character = Character(
                    screencenterdpX -(characterWidth / 2),
                    screencenterdpY,
                    widthDp = characterWidth,
                    heightDp = characterHeight,
                ),
                isGameOver = false,
                platforms = plList,
                records = recordslist
            )
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
        val platformShift =
            if (newY <= topborderYDp && newVelocityY < 0) topborderYDp - newY else 0f


        val scrollThreshold: Float = 400f
         val scrollOffset = if (newY < scrollThreshold) scrollThreshold - newY else 0f


        if (newVelocityY > 0) {

            state.platforms.forEach { p ->

                val isxcoordinatecorrect: Boolean =
                    (state.character.xDp + state.character.widthDp) in p.xDp..(p.xDp + p.widthDp) || (p.xDp + p.widthDp) in state.character.xDp..(state.character.xDp + state.character.widthDp)


                if (
                    newY + state.character.heightDp - state.character.mergeDp >= p.yDp && state.character.yDp + state.character.heightDp <= p.yDp + p.heightDp && isxcoordinatecorrect
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


         val newCharY = if (newY <= topborderYDp && newVelocityY < 0) topborderYDp else if (collided) state.character.yDp else newY;

        //val newCharY = if (collided) state.character.yDp else newY;


        var minY = screenHeightDp

        state.platforms.forEach{ p ->
            minY = if(p.yDp < minY){p.yDp} else {minY}



        }


        state.copy(
            character = state.character.copy(yDp = newCharY),

            velocityY = finalVelocityY,
            score = state.score + scrollOffset.toInt(),
            isGameOver = finalY > 2000f,



             platforms = state.platforms.map { p ->
                 val moveplatformY = p.yDp + platformShift
                 if (moveplatformY > screenHeightDp) {

                     val randomVal = (maxStepSize.toInt()/3..maxStepSize.toInt()).random();

                     p.copy(
                         yDp = (minY - randomVal),
                         xDp = (0..screenWidthDp.toInt()-p.widthDp.toInt()).random().toFloat())
                 } else
                     p.copy(yDp = moveplatformY)

             },


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

