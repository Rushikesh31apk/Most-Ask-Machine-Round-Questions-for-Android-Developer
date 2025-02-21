package com.rushikesh31apk.my_practice.room_practice.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("todoHome")
    object AddScreen : Screen("todoAdd")
}
