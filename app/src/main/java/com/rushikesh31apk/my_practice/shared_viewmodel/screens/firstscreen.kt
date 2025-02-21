package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rushikesh31apk.my_practice.AppNavigation.BottomNavigationBar
import com.rushikesh31apk.my_practice.AppNavigation.Bottombar
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel
import com.rushikesh31apk.my_practice.shared_viewmodel.UserData

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirstScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(2) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details", fontSize = 22.sp, color = Color.White) },
                modifier = Modifier.shadow(8.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EA))
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Enter your data",
                fontSize = 24.sp,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    sharedViewModel.updateUserData(
                        UserData(id = id.toIntOrNull() ?: 0, name = name, email = email)
                    )
                    navController.navigate(Bottombar.SecondScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "Next", tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Show Data", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    sharedViewModel.fetchProducts()
                    navController.navigate(Bottombar.ProductsScreen.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC6)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.List, contentDescription = "View Products", tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Products", color = Color.Black)
            }
        }
    }
}
