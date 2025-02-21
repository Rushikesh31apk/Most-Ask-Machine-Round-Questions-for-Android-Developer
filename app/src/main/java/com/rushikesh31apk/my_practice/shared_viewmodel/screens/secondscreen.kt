package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel


// 3. Second Screen - Receiving data
@Composable
fun SecondScreen(sharedViewModel: SharedViewModel) {
    val userData by sharedViewModel.userData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        userData.let { user ->
            Text("User ID: ${user?.id}")
            Text("Name: ${user?.name}")
            Text("Email: ${user?.email}")
        }
    }
}