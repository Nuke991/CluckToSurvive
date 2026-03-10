package com.mk.clucktosurvive.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mk.clucktosurvive.R
import com.mk.clucktosurvive.theme.DeepBlue




@Composable
fun MenuScreen(
    onStartClick: () -> Unit,
    onRecordsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val startIntercation = remember { MutableInteractionSource() }
    val recordsIntercation = remember { MutableInteractionSource() }
    val policyIntercation = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.basic_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(vertical = 12.dp)
                    .height(70.dp)
                    .clickable(
                        interactionSource = startIntercation,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onStartClick
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.standard_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.menu_button_start),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(vertical = 12.dp)
                    .height(70.dp)
                    .clickable(
                        interactionSource = recordsIntercation,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onRecordsClick
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.standard_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.menu_button_records),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )
            }


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .padding(vertical = 12.dp)
                    .height(70.dp)
                    .clickable(
                        interactionSource = policyIntercation,
                        indication = ripple(bounded = false, color = Color.Black.copy(alpha = 0.2f)),
                        onClick = onPrivacyClick
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.standard_button),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = stringResource(id = R.string.menu_button_privacy),
                    style = MaterialTheme.typography.bodyLarge,
                    color = DeepBlue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    MenuScreen(
        onStartClick = {},
        onRecordsClick = {},
        onPrivacyClick = {}
    )
}

