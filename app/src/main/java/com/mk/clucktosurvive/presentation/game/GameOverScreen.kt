package com.mk.clucktosurvive.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.clucktosurvive.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameOverScreen(viewModel: GameViewModel = koinViewModel(), onPlayAgain: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()



    GameOverScreenContent(onPlayAgain,onBack,state)

}

@Composable
fun GameOverScreenContent(onPlayAgain: () -> Unit, onBack: () -> Unit, state:GameUiState) {
    val backInteraction = remember { MutableInteractionSource() }
    val playAgainInteraction = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.gameoverscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 65.dp, start = 30.dp)
                .size(55.dp)
                .clickable(
                    interactionSource = backInteraction,
                    indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.3f)),
                    onClick = onBack
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.returnbutton),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.game_over_text),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.8f),
                contentScale = ContentScale.FillWidth
            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(vertical = 20.dp).size(width = 150.dp, height = 65.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.record_button),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = "${state.score}M",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.deep_blue),
                    )
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(80.dp)
                    .clickable(
                        interactionSource = playAgainInteraction,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onPlayAgain
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.standard_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.game_over_play_again),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.deep_blue)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    GameOverScreenContent(
        onPlayAgain = {},
        onBack = {},
        state = GameUiState(
        )

    )
}
