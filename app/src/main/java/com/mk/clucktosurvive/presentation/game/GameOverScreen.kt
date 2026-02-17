package com.mk.clucktosurvive.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameOverScreen(viewModel: GameViewModel = koinViewModel(), onPlayAgain: () -> Unit, onBack: () -> Unit) {

    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.gameoverscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.align(Alignment.TopStart).padding(top = 65.dp, start = 30.dp)
                .size(55.dp).clickable { onBack() }) {

            Image(
                painter = painterResource(id = R.drawable.returnbutton),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.gameovertext),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = 76.dp, y = 254.dp)
                    .size(width = 260.dp, height = 191.dp),
                contentScale = ContentScale.Crop
            )


        }

        Box(
            modifier = Modifier
                .offset(x = 146.dp, y = 468.dp)
                .size(width = 119.dp, height = 55.dp),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.small_recordsbutton),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )


            Text(
                text = "${state.score}M",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            )

        }
        Box(
            modifier = Modifier
                .offset(x = 78.dp, y = 546.dp)
                .size(width = 255.dp, height = 70.dp)
                .clickable { onPlayAgain() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.playagainbutton),
                contentDescription = "Play Again",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}