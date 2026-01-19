package com.mk.clucktosurvive.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.clucktosurvive.R




@Composable
fun MenuScreen(
    onStartClick: () -> Unit,
    onRecordsClick : () -> Unit,
    onPrivacyClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.menu_main),
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
                painter = painterResource(id = R.drawable.start_button),
                contentDescription = "Start Button",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onStartClick() }
            )

            Image(
                painter = painterResource(id = R.drawable.records_button),
                contentDescription = "Start Button",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onRecordsClick() }
            )

            Image(
                painter = painterResource(id = R.drawable.privacypolicy_button),
                contentDescription = "PrivacyPolicy_Button",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
                    .clickable { onPrivacyClick() }
            )

        }
    }
}


