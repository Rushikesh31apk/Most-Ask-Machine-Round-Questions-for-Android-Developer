package com.rushikesh31apk.my_practice.AppNavigation

sealed class Bottombar(val route: String) {
    object TodosScreen : Bottombar("todos_screen")
    object MarsUiScreen : Bottombar("mars_ui_screen")
    object SharedViewModelScreen : Bottombar("shared_view_model_screen")
    object SharedPrefScreen : Bottombar("shared_pref_screen")
    object RegisterScreen : Bottombar("register_screen")
    object SecondScreen : Bottombar("second_screen")
    object ProductsScreen : Bottombar("products_screen")
    object LoginScreen : Bottombar("login")
    object HomeScreen : Bottombar("home")
    object AddTodoScreen : Bottombar("add_todo_screen")
}
