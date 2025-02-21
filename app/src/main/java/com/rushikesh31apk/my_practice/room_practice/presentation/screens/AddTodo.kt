package com.rushikesh31apk.my_practice.room_practice.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rushikesh31apk.my_practice.AppNavigation.Bottombar
import com.rushikesh31apk.my_practice.room_practice.data.model.Todo
import com.rushikesh31apk.my_practice.room_practice.presentation.TodoViewModel

@Composable
fun AddTodo(viewModel: TodoViewModel = viewModel(), navController: NavController) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "",

                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.navigate(
                            Bottombar.TodosScreen.route
                        )
                    })

            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Add Todo", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.text.isNotBlank() && description.text.isNotBlank()) {
                    val todo = Todo(title.text, description.text)
                    viewModel.addTodo(todo)
                    title = TextFieldValue("")
                    description = TextFieldValue("")
                    navController.navigateUp()
                    // Navigate back or update UI
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "Add Todo", color = Color.White)
        }
    }
}


