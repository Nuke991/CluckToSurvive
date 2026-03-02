package com.mk.clucktosurvive.presentation.loading

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mk.clucktosurvive.presentation.privacypolicy.PrivacyScreen

@Composable
fun LoadScreen(onTimeout: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.load_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.load_logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
                .fillMaxWidth(0.65f)
        )

        Text(
            text = stringResource(id=R.string.loading_text),
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 787.dp),
            style = TextStyle(color = Color.White,fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold)

        )





    }
}

@Preview(showBackground = true)
@Composable
fun loadingscreenpreview(){
    LoadScreen(
        onTimeout = {}
    )
}