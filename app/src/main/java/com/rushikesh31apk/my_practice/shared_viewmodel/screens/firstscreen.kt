package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel
import com.rushikesh31apk.my_practice.shared_viewmodel.UserData

// 2. First Screen - Sending data
@Composable
fun FirstScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter your data",
            fontSize = 20.sp,
            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID") }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )



        Button(
            onClick = {
                // Update ViewModel data
                sharedViewModel.updateUserData(
                    UserData(id = id.toInt() , name=name, email = email)
                )
                // Navigate to second screen
                navController.navigate("second_screen")
            }
        ) {
            Text("show data")
        }

        Button(
            onClick = {
                // Fetch large dataset
                sharedViewModel.fetchProducts()
                navController.navigate("products_screen")
            }
        ) {
            Text("View Products")
        }
    }
}