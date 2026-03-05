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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.mk.clucktosurvive.theme.MainButtonColor

@Composable
fun PauseScreen(onResume: () -> Unit, onExit: () -> Unit) {
    val resumeIntercation = remember { MutableInteractionSource() }
    val exitIntercation = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.pausescreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.pausetext),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                    contentScale = ContentScale.FillWidth
            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(100.dp)
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = resumeIntercation,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onResume
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pause_resume),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.pause_resume),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MainButtonColor
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(100.dp)
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = exitIntercation,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onExit
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pause_exit),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.pause_button_exit),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MainButtonColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pausescreenpreview(){
    PauseScreen(
        onResume = {},
        onExit = {}
    )
}