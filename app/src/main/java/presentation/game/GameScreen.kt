package presentation.game


import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mk.clucktosurvive.R
import presentation.components.PauseButton
import androidx.compose.runtime.remember
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onPauseClick: () -> Unit,
    onGameOver: () -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val density = androidx.compose.ui.platform.LocalDensity.current.density
    val characterBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.character).asImageBitmap()
    }
    val platformBigBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.platform_big).asImageBitmap()
    }



    Box(modifier = Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        val widthPx = coordinates.size.width.toFloat()
        val heightPx = coordinates.size.height.toFloat()
        viewModel.updateScreenSize(widthPx, heightPx, density)
        viewModel.resetGame(
            widthPx = characterBitmap.width,
            heightPx = characterBitmap.height,
            density = density
        );
        viewModel.startGame();

    }.draggable( orientation = Orientation.Horizontal, state = rememberDraggableState {delta -> viewModel.onDrag(delta)}))
    {
        Image(
            painter = painterResource(id = R.drawable.gamescreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        PauseButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 65.dp, start = 30.dp),
            onPauseClick = onPauseClick
        )


        state.platforms.forEach { platform ->

            Image(
                painter = painterResource(id = R.drawable.platform_big),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = platform.x.dp, y = platform.y.dp)
                    .size(platform.width.dp, platform.height.dp),
                contentScale = ContentScale.FillBounds
            )

        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val imageWidth = characterBitmap.width
            val imageHeight = characterBitmap.height
            drawImage(
                image = characterBitmap,
                dstOffset = IntOffset(
                    (state.character.x * density).toInt(),
                    (state.character.y * density).toInt()
                ),
                dstSize = IntSize(
                    characterBitmap.width,
                    characterBitmap.height
                )
            )
        }

            /*Image(
            painter = painterResource(id = R.drawable.character),
            null,
            modifier = Modifier
                .offset(
                    x = state.character.x.dp,
                    y = state.character.y.dp
                )
                .size(42.dp, 70.dp),
            contentScale = ContentScale.FillBounds


        )*/


    }
}

