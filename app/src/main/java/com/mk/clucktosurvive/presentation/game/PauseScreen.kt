package com.mk.clucktosurvive.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R

@Composable
fun PauseScreen(onResume: () -> Unit, onExit: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.pausescreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.pausetext),
                contentDescription = "pause",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onResume() }
            )

            Image(
                painter = painterResource(id = R.drawable.resume),
                contentDescription = "resume_Button",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onResume() }
            )

            Image(
                painter = painterResource(id = R.drawable.exit),
                contentDescription = "exit_Button",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onExit() }
            )

        }

    }
}