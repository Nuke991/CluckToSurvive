package com.mk.clucktosurvive.presentation.privacypolicy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mk.clucktosurvive.R
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.mk.clucktosurvive.presentation.components.ReturnButton

@Composable
fun PrivacyScreen(onBack:() -> Unit)
{
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.privacyscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        ReturnButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 65.dp, start = 30.dp),
                onBack = onBack)



        Text("PRIVACY POLICY", style = TextStyle(color = Color.White,fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold), modifier = Modifier.align(Alignment.TopStart).padding(top = 124.dp, start = 100.dp))

    }
}