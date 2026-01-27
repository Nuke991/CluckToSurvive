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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mk.clucktosurvive.R
import presentation.components.PauseButton

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onPauseClick: () -> Unit,
    onGameOver: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()


    Box(modifier = Modifier.fillMaxSize().draggable( orientation = Orientation.Horizontal, state = rememberDraggableState {delta -> viewModel.onDrag(delta)}))
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

        Image(
            painter = painterResource(id = R.drawable.character),
            null,
            modifier = Modifier
                .offset(
                    x = state.character.x.dp,
                    y = state.character.y.dp
                )
                .size(42.dp, 70.dp),
            contentScale = ContentScale.FillBounds


        )


    }
}

