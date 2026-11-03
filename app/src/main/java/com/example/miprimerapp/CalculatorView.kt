package com.example.miprimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource

import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
//para poder hacer las operaciones se vayan hacia la izq
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.TextUnit

//parte de navegacion de historial
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
@Composable
fun ShowCalculator(
    viewModel: CalculatorViewModel = viewModel(),
    navController: NavController
) {
    val result = viewModel.result.value
    val expression = viewModel.expression.value
    val error = viewModel.error.value
    val resultText = when (error) {
        CalculatorError.None -> result
        CalculatorError.DivisionByZero -> stringResource(id = R.string.divisionByZeroError)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.black))
            .safeDrawingPadding()
    ) {
        // ZONA 1: BARRA SUPERIOR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(historyRoute) },                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.numberButton)),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_history),
                    contentDescription = stringResource(id = R.string.historyDescription),
                    tint = colorResource(id = R.color.white)
                )
            }
            Button(
                onClick = { /* : se hará después */ },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.numberButton)),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_scientific),
                    contentDescription = stringResource(id = R.string.scientificDescription),
                    tint = colorResource(id = R.color.white)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {

            // Expresión (gris)
            ShowDisplayText(text = expression, fontSize = 40.sp, color = colorResource(id = R.color.expressionGray))
            // Resultado (blanco)
            ShowDisplayText(text = resultText, fontSize = 75.sp, color = colorResource(id = R.color.white))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(stringResource(id= R.string.backspaceSymbol), colorResource(id = R.color.functionButton), viewModel)
                    ShowButton(stringResource(id= R.string.clearSymbol), colorResource(id = R.color.functionButton), viewModel)
                    ShowButton(stringResource(id= R.string.percentSymbol), colorResource(id = R.color.functionButton), viewModel, isOperator = true)
                    ShowButton(stringResource(id= R.string.divideSymbol), colorResource(id = R.color.operationButton), viewModel ,isOperator = true)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(stringResource(id= R.string.digitSeven), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitEight), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitNine), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.multiplySymbol), colorResource(id = R.color.operationButton), viewModel ,isOperator = true)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(stringResource(id= R.string.digitFour), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitFive), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitSix), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.subtractionSymbol), colorResource(id = R.color.operationButton), viewModel ,isOperator = true)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(stringResource(id= R.string.digitOne), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitTwo), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitThree), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.addSymbol), colorResource(id = R.color.operationButton), viewModel ,isOperator = true)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ShowButton(stringResource(id= R.string.changeSymbol), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.digitZero), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.decimalSymbol), colorResource(id = R.color.numberButton), viewModel)
                    ShowButton(stringResource(id= R.string.equalSymbol), colorResource(id = R.color.operationButton), viewModel)
                }
            }
        }
    }
}
@Composable
fun ShowButton(text: String, color: Color, viewModel: CalculatorViewModel, isOperator: Boolean = false) {
    val equalSymbol = stringResource(id = R.string.equalSymbol)
    val clearSymbol = stringResource(id = R.string.clearSymbol)
    val backspaceSymbol = stringResource(id = R.string.backspaceSymbol)
    val changeSymbol = stringResource(id = R.string.changeSymbol)
    val subtractionSymbol = stringResource(id = R.string.subtractionSymbol)
    val addSymbol = stringResource(id = R.string.addSymbol)
    val multiplySymbol = stringResource(id = R.string.multiplySymbol)
    val divideSymbol = stringResource(id = R.string.divideSymbol)
    val percentSymbol = stringResource(id = R.string.percentSymbol)
    Button(
        onClick = {
            //se debe mantener el orden que hay en CalculatorViewModel
            viewModel.manageClickButtons(text, equalSymbol, clearSymbol, backspaceSymbol, changeSymbol, subtractionSymbol, addSymbol, multiplySymbol, divideSymbol, percentSymbol, isOperator)
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(color),
        modifier = Modifier
            .size(85.dp)
    ) {
        Text(
            text = text,
            fontSize = 28.sp
        )
    }
}
@Composable
fun ShowDisplayText(text: String, fontSize: TextUnit, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 5.dp, start = 5.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            modifier = Modifier.horizontalScroll(rememberScrollState(), reverseScrolling = true),
            text = text,
            fontSize = fontSize,
            color = color,
            softWrap = false,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestView(){
    ShowCalculator(navController = rememberNavController())
}