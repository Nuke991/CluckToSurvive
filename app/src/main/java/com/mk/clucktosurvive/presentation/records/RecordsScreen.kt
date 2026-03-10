package com.mk.clucktosurvive.presentation.records


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.clucktosurvive.R
import com.mk.clucktosurvive.domain.model.ScoreRecord
import com.mk.clucktosurvive.presentation.components.ReturnButton
import org.koin.androidx.compose.koinViewModel
import com.mk.clucktosurvive.theme.DeepBlue


@Composable
fun RecordsScreen(
    recordsScreenViewModel: RecordsScreenViewModel = koinViewModel(),
    onBack: () -> Unit)
{

    val state by recordsScreenViewModel.uiState.collectAsStateWithLifecycle()



    RecordScreenContent(onBack, state)


}

@Composable
private fun RecordScreenContent(
    onBack: () -> Unit,
    state: RecordsUiState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.basic_screen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        ReturnButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 65.dp, start = 30.dp),
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(124.dp))

            Text(
                text = stringResource(id = R.string.records_screen_head),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Spacer(modifier = Modifier.height(30.dp))


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.records) { record ->
                    RecordItem(record)
                }
            }
        }
    }
}


@Composable
fun RecordItem(record: ScoreRecord) {
    Box(
        modifier = Modifier
            .width(270.dp)
            .height(75.dp),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.record_panel),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 45.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = record.date,
                color = colorResource(id = R.color.deep_blue),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${record.score}M",
                color = colorResource(id = R.color.deep_blue),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordsPreview() {
    val mockRecords = listOf(
        ScoreRecord(date = "05.03.2024", score = 150),
        ScoreRecord(date = "04.03.2024", score = 92),
        ScoreRecord(date = "01.03.2024", score = 45)
    )

    RecordScreenContent(
        onBack = {},
        state = RecordsUiState(records = mockRecords)
    )
}