package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel

@Composable
fun SecondScreen(sharedViewModel: SharedViewModel) {
    val userData by sharedViewModel.userData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEDED)), // Light gray background
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Responsive width
                .shadow(12.dp, RoundedCornerShape(16.dp)) // 3D effect
                .background(Color.White),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "User Information",
                    fontSize = 22.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                userData?.let { user ->
                    UserInfoRow("User ID: ", user.id.toString())
                    UserInfoRow("Name: ", user.name)
                    UserInfoRow("Email: ", user.email)
                } ?: Text("No user data available", fontSize = 18.sp, color = Color.Red)
            }
        }
    }
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(6.dp, RoundedCornerShape(12.dp)), // Slight shadow for 3D effect
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 18.sp, color = Color.DarkGray)
            Text(text = value, fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}
