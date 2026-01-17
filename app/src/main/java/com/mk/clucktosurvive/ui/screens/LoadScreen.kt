package com.mk.clucktosurvive.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.mk.clucktosurvive.R

@Composable
fun LoadScreen(onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.bg_load),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}