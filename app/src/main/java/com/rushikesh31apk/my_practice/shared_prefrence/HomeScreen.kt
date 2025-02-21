package com.rushikesh31apk.my_practice.shared_prefrence

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context
) {
    val userData = PreferencesManager.getUserData(context)
    var selectedIndex by remember { mutableStateOf(4) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth().shadow(8.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF61EA00)),
                title = {
                    Text("Home", fontSize = 22.sp, color = Color.White)
                }
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
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF4B4F6), Color(0xFFF6423D))
                    )
                )
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "User Icon",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome, ${userData.first}!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    PreferencesManager.clearUserData(context)
                    navController.navigate("login") {
                        popUpTo(Bottombar.LoginScreen.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", color = Color.Black)
            }
        }
    }
}
