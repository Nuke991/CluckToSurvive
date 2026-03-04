package com.mk.clucktosurvive.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.clucktosurvive.R
import com.mk.clucktosurvive.domain.model.ScoreRecord
import com.mk.clucktosurvive.domain.model.repository.RecordRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Character(
    val xDp: Float = 0f,
    val yDp: Float = 0f,
    val widthDp: Float = 0f,
    val heightDp: Float = 0f,
)


enum class PlatformType(val resourceId: Int) {
    SMALL(R.drawable.platform_small),
    BIG(R.drawable.platform_big),

}


enum class GameScreenMove(val value: Int) {
    MOVETOP(1),
    MOVEDOWN(-1),
    FIXED(0);
}


data class Platform(
    val platformType: PlatformType,
    val xDp: Float,
    val yDp: Float,
    val widthDp: Int,
    val heightDp: Int,
)

data class GameUiState(
    val gameScreenMove: GameScreenMove = GameScreenMove.FIXED,
    val character: Character = Character(),
    val velocityY: Float = 0f,
    val score: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false,
    val platforms: List<Platform> = listOf(),
    val records: List<Record> = listOf(),
)


class GameViewModel(var repository: RecordRepository) : ViewModel() {

    private  val MERGE_Y = 17f

    private var screenWidthDp: Float = 0f
    public var screenHeightDp: Float = 0f
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    private var gameJob: Job? = null
    private val gravity = 0.8f
    private val jumpImpulse = -20f
    private val topborderYDp = 400f
    private val downborderY = 800f

    private val maxStepSize: Float = 200f


    fun resetGame(
        charWidthPx: Float,
        charHeightPx: Float,
        platformSize: Map<PlatformType, Pair<Int, Int>>
    ) {
        val characterWidth = charWidthPx
        val characterHeight = charHeightPx

        val screenCenterDpX = screenWidthDp * 0.5f
        val screenCenterDpY = screenHeightDp * 0.5f

        val plList = mutableListOf<Platform>()

        var platformYdp = 600f
        var isFirst: Boolean = true


        do {
            val platformType: PlatformType = PlatformType.entries[(0..1).random()]

            val platformSize = platformSize.getValue(platformType)
            var bitmapWidth = platformSize.first
            var bitmapHeight = platformSize.second


            val platformXdp: Float = if (isFirst) {
                screenCenterDpX - bitmapWidth / 2
            } else {
                (0..screenWidthDp.toInt() - bitmapWidth).random().toFloat()

            }

            val newPlatform = Platform(
                platformType,
                platformXdp,
                platformYdp,
                bitmapWidth,
                bitmapHeight
            )
            plList.add(newPlatform)

            platformYdp -= ((maxStepSize / 2).toInt()..maxStepSize.toInt()).random().toFloat()
            isFirst = false
        } while (platformYdp >= -2 * maxStepSize)



        _uiState.update {
            it.copy(
                character = Character(
                    screenCenterDpX - (characterWidth / 2),
                    screenCenterDpY,
                    widthDp = characterWidth,
                    heightDp = characterHeight,

                    ),
                score = 0,
                isGameOver = false,
                platforms = plList,

                )
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
        val newY = state.character.yDp + newVelocityY
        var finalVelocityY = newVelocityY
        var collided = false
        val platformShift =
            if (newY <= topborderYDp && newVelocityY < 0) topborderYDp - newY else 0f


        if (newVelocityY > 0) {
            state.platforms.forEach { p ->
                val isXCoordinateCorrect: Boolean =
                    (state.character.xDp + state.character.widthDp) in p.xDp..(p.xDp + p.widthDp) || (p.xDp + p.widthDp) in state.character.xDp..(state.character.xDp + state.character.widthDp)

                if (
                    newY + state.character.heightDp - MERGE_Y >= p.yDp && state.character.yDp + state.character.heightDp <= p.yDp + p.heightDp && isXCoordinateCorrect
                ) {
                    collided = true
                    finalVelocityY = jumpImpulse
                }
            }
        }


        val gameScreenMoveState = if (newY <= topborderYDp) {
            GameScreenMove.MOVEDOWN
        } else if (newY >= downborderY) {
            GameScreenMove.MOVETOP
        } else GameScreenMove.FIXED


        val finalY =
            if (gameScreenMoveState == GameScreenMove.MOVEDOWN && newVelocityY < 0) {
                topborderYDp
            } else {
                (if (collided) state.character.yDp else newY)
            }


        val newCharY =
            if (newY <= topborderYDp && newVelocityY < 0) topborderYDp else if (collided) state.character.yDp else newY


        val newPlatforms = recalcPlatforms(state, platformShift, screenHeightDp)

        val isGameOver = (finalY > screenHeightDp)

        checkGameOver(isGameOver, state)

        state.copy(
            character = state.character.copy(yDp = newCharY),
            velocityY = finalVelocityY,
            score = state.score + platformShift.toInt(),
            isGameOver = isGameOver,
            platforms = newPlatforms,
        )
    }

    private fun checkGameOver(
        isGameOver: Boolean,
        state: GameUiState
    ) {
        if (isGameOver) {
            viewModelScope.launch {
                val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
                val currentDate = dateFormat.format(Date())
                repository.addRecord(ScoreRecord(currentDate, state.score))
            }
            gameJob?.cancel()
        }
    }

    private fun recalcPlatforms(
        state: GameUiState,
        platformShift: Float,
        screenHeightDp: Float
    ): List<Platform> {
        var minY = screenHeightDp

        state.platforms.forEach { p ->
            minY = if (p.yDp < minY) {
                p.yDp
            } else {
                minY
            }
        }

        val newPlatforms = state.platforms.map { p ->
            val moveplatformY = p.yDp + platformShift
            if (moveplatformY > screenHeightDp) {

                val randomVal = (maxStepSize.toInt() / 3..maxStepSize.toInt()).random()

                p.copy(
                    yDp = (minY - randomVal),
                    xDp = (0..screenWidthDp.toInt() - p.widthDp).random().toFloat()
                )
            } else
                p.copy(yDp = moveplatformY)

        }
        return newPlatforms
    }

    fun onDrag(deltaX: Float, density: Float) {
        _uiState.update { state ->
            val newX =
                (state.character.xDp + deltaX / density).coerceIn(
                    0f,
                    screenWidthDp - state.character.widthDp
                )
            state.copy(character = state.character.copy(xDp = newX))
        }
    }

    fun onPauseClick() = _uiState.update { it.copy(isPaused = true) }
    fun onResumeClick() = _uiState.update { it.copy(isPaused = false) }


    fun updateScreenSize(widthPx: Float, heightPx: Float, density: Float) {
        screenWidthDp = widthPx / density
        screenHeightDp = heightPx / density

    }


}

