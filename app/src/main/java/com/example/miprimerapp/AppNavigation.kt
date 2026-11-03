package com.example.miprimerapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

val calculatorRoute = "calculator"
val historyRoute = "history"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: CalculatorViewModel = viewModel()

    NavHost(navController = navController, startDestination = calculatorRoute) {
        composable(calculatorRoute) {
            ShowCalculator(viewModel = viewModel, navController = navController)
        }
        composable(historyRoute) {
            ShowHistory(viewModel = viewModel, navController = navController)
        }
    }
}