package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel

// 6. NavHost setup
@Composable
fun NavigationSetup() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "first_screen"
    ) {
        composable("first_screen") {
            FirstScreen(sharedViewModel, navController)
        }
        composable("second_screen") {
            SecondScreen(sharedViewModel)
        }
        composable("products_screen") {
            ProductsScreen(sharedViewModel)
        }
    }
}