package com.mk.clucktosurvive.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import com.mk.clucktosurvive.ui.components.ReturnButton

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



        Text("PRIVACY POLICY", style = TextStyle(color = androidx.compose.ui.graphics.Color.White,fontSize = 25.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold), modifier = Modifier.align(Alignment.TopStart).padding(top = 124.dp, start = 100.dp))

    }
}