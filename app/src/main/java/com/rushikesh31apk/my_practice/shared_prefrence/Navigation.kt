package com.rushikesh31apk.my_practice.shared_prefrence

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Navigation Setup
@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val startDestination = if (PreferencesManager.getUserData(context).third) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController, context)
        }
        composable("home") {
            HomeScreen(navController, context)
        }
    }
}