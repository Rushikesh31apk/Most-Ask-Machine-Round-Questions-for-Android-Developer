package com.rushikesh31apk.my_practice.AppNavigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quickreply
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rushikesh31apk.my_practice.marsApi.ui_layer.MarsUiScreen
import com.rushikesh31apk.my_practice.room_practice.presentation.screens.AddTodo
import com.rushikesh31apk.my_practice.room_practice.presentation.screens.Todos
import com.rushikesh31apk.my_practice.shared_prefrence.HomeScreen
import com.rushikesh31apk.my_practice.shared_prefrence.LoginScreen
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel
import com.rushikesh31apk.my_practice.shared_viewmodel.screens.FirstScreen
import com.rushikesh31apk.my_practice.shared_viewmodel.screens.ProductsScreen
import com.rushikesh31apk.my_practice.shared_viewmodel.screens.SecondScreen
import com.rushikesh31apk.my_practice.ui_parctice.RegisterScreen


@Composable
fun AppNavHost(navController: NavHostController) {

    val sharedViewModel: SharedViewModel = viewModel()
    val context = navController.context

    NavHost(navController = navController, startDestination = Bottombar.TodosScreen.route) {
        composable(Bottombar.TodosScreen.route) { Todos(navController = navController) }
        composable(Bottombar.MarsUiScreen.route) { MarsUiScreen(navController=navController) }
        composable(Bottombar.SharedViewModelScreen.route) { FirstScreen(sharedViewModel = sharedViewModel,navController=navController) }
        composable(Bottombar.SharedPrefScreen.route) { LoginScreen(navController= navController, context = context) }
        composable(Bottombar.RegisterScreen.route) { RegisterScreen(navController) }

        composable(Bottombar.SecondScreen.route) { SecondScreen(sharedViewModel) }
        composable(Bottombar.ProductsScreen.route) { ProductsScreen(sharedViewModel) }
        composable(Bottombar.LoginScreen.route) { LoginScreen(navController, context) }
        composable(Bottombar.HomeScreen.route) { HomeScreen(navController, context) }
        composable(Bottombar.AddTodoScreen.route) { AddTodo(navController = navController) }


    }
}



@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        NavigationItem("Room", Icons.Default.Storage, Bottombar.TodosScreen.route),
        NavigationItem("Mars", Icons.Default.Wifi, Bottombar.MarsUiScreen.route),
        NavigationItem("Shared view", Icons.Default.Api, Bottombar.SharedViewModelScreen.route),
        NavigationItem("Shared pref", Icons.Default.Quickreply, Bottombar.SharedPrefScreen.route),
        NavigationItem("Ui", Icons.Default.Person, Bottombar.RegisterScreen.route)
    )

    Card(
        shape = RoundedCornerShape(bottomStartPercent = 0, bottomEndPercent = 0, topStartPercent = 40, topEndPercent = 40),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = Color.Green,
            contentColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = {
                        onItemSelected(index) // Update selected index
                        navController.navigate(item.route) // Navigate to the selected screen
                    },
                    icon = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedIndex == index) Color(0xFF6200EE) else Color.Gray,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF6200EE),
                        unselectedIconColor = Color.White,
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
data class NavigationItem(val title: String, val icon: ImageVector, val route: String)


