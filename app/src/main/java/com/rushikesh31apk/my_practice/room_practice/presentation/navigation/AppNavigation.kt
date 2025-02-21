package com.rushikesh31apk.my_practice.room_practice.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rushikesh31apk.my_practice.room_practice.presentation.screens.AddTodo
import com.rushikesh31apk.my_practice.room_practice.presentation.screens.Todos

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route){
            Todos(navController= navController)
        }
        composable(Screen.AddScreen.route){
            AddTodo(navController = navController)
        }
    }
}