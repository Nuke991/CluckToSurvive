package com.mk.clucktosurvive.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R

@Composable
fun PauseButton(onPauseClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(55.dp)
            .clickable { onPauseClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.pausebutton),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}