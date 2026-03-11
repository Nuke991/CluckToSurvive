package com.mk.clucktosurvive.presentation.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import kotlinx.coroutines.delay

@Composable
fun LoadScreen(onTimeout: () -> Unit) {

    val transition = rememberInfiniteTransition(label = "rotation")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing)
        ),
        label = "angle"
    )

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.loading_screen),
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
                .padding(top = 100.dp)
                .fillMaxWidth(0.7f)
        )


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.loading_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(27.dp)
                    .graphicsLayer { rotationZ = angle }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = stringResource(id = R.string.loading_text),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
                )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Loadingscreenpreview(){
    LoadScreen(
        onTimeout = {}
    )
}