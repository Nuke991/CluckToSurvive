package com.mk.clucktosurvive.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.mk.clucktosurvive.theme.DarkGreen
import com.mk.clucktosurvive.theme.DeepRed
import com.mk.clucktosurvive.theme.MainButtonColor

@Composable
fun PauseScreen(onResume: () -> Unit, onExit: () -> Unit) {
    val resumeInteraction = remember { MutableInteractionSource() }
    val exitInteraction = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(enabled = false, onClick = {})
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.pausetext),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.FillWidth
            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(90.dp)
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = resumeInteraction,
                        indication = ripple(color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onResume
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.green_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.pause_resume),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkGreen
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(90.dp)
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = exitInteraction,
                        indication = ripple(color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onExit
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.red_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.pause_button_exit),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepRed
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