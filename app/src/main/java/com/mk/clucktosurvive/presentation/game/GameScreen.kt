package com.mk.clucktosurvive.presentation.game


import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import com.mk.clucktosurvive.presentation.components.PauseButton
import androidx.compose.runtime.remember
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    onGameOver: () -> Unit,
    onExit: () -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    var isScreenReady by remember { mutableStateOf(false) }


    // если игра окончена → навигация
    if (state.isGameOver) {
        onGameOver()
        state.isGameOver = false;
    }




    val density = androidx.compose.ui.platform.LocalDensity.current.density
    val characterBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.character1).asImageBitmap()
    }


    if(state.isPaused){
        PauseScreen(
            onResume = { viewModel.onResumeClick()},
            onExit = onExit
        )

    } else






    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { coordinates ->
            if (!isScreenReady) {
                val widthPx = coordinates.size.width.toFloat()
                val heightPx = coordinates.size.height.toFloat()
                viewModel.updateScreenSize(widthPx, heightPx, density)
                viewModel.resetGame(
                    charWidthPx = characterBitmap.width.toFloat(),
                    charHeightPx = characterBitmap.height.toFloat(),
                    context,
                    density

                )

                isScreenReady = true
            }


        }
        .draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta -> viewModel.onDrag(delta) }))
    {
        Image(
            painter = painterResource(id = R.drawable.gamescreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Canvas(modifier = Modifier.fillMaxSize()) {

            state.platforms.forEach { platform ->

                drawImage(
                    image = platform.platformBitmap,
                    dstOffset = IntOffset(
                        (platform.xDp * density).toInt(),
                        (platform.yDp * density).toInt()
                    ),
                    dstSize = IntSize(
                        platform.platformBitmap.width,
                        platform.platformBitmap.height
                    )
                )
            }

            drawImage(
                image = characterBitmap,
                dstOffset = IntOffset(
                    (state.character.xDp * density).toInt(),
                    (state.character.yDp * density).toInt()
                ),
                dstSize = IntSize(
                    (characterBitmap.width).toInt(),
                    (characterBitmap.height).toInt()
                )
            )
        }
        PauseButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 65.dp, start = 30.dp),
            onPauseClick = {

                viewModel.onPauseClick()

            }
        )

        Box(
            modifier = Modifier
                .padding(top = 65.dp, end = 30.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.records_screen),
                contentDescription = null,
                modifier = Modifier.size(width = 119.dp, height = 55.dp)
            )
            Text(
                text = "${state.score}M",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }


    }

    if (isScreenReady) {
        LaunchedEffect(Unit) {
            viewModel.startGame()
        }
    }
}




