package com.example.miprimerapp


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ShowHistory(viewModel: CalculatorViewModel, navController: NavController) {
    val equalSymbol = stringResource(id = R.string.equalSymbol)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = stringResource(id = R.string.backButton))
        }

        if (viewModel.history.isEmpty()) {
            Text(
                text = stringResource(id = R.string.historyEmpty),
                color = colorResource(id = R.color.white),
                fontSize = 24.sp
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(viewModel.history) { entry ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "${entry.expression} $equalSymbol",
                            color = colorResource(id = R.color.functionButton),
                            fontSize = 24.sp,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = entry.result,
                            color = colorResource(id = R.color.white),
                            fontSize = 40.sp
                        )
                    }
                }
            }
        }
    }
}