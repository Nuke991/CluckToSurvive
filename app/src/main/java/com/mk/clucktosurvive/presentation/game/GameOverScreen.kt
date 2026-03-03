package com.mk.clucktosurvive.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mk.clucktosurvive.theme.MainButtonColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameOverScreen(viewModel: GameViewModel = koinViewModel(), onPlayAgain: () -> Unit, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

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
                .clickable { onBack() }
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
                painter = painterResource(id = R.drawable.gameovertext),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.9f),
                contentScale = ContentScale.FillWidth
            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(vertical = 20.dp).size(width = 150.dp, height = 65.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.small_recordsbutton),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = "${state.score}M",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 22.sp,
                        color = Color.White
                    )
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(80.dp)
                    .clickable { onPlayAgain() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.button_playagain),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.game_over_play_again),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MainButtonColor
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    GameOverScreen(
        onPlayAgain = {},
        onBack = {}
    )
}
