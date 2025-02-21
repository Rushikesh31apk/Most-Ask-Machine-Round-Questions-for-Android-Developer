package com.rushikesh31apk.my_practice.room_practice.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rushikesh31apk.my_practice.AppNavigation.BottomNavigationBar
import com.rushikesh31apk.my_practice.AppNavigation.Bottombar
import com.rushikesh31apk.my_practice.room_practice.data.model.Todo
import com.rushikesh31apk.my_practice.room_practice.presentation.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Todos(viewModel: TodoViewModel = viewModel(), navController: NavHostController) {

    val alltodos by viewModel.todos.collectAsState()
    var selectedIndex by remember { mutableStateOf(0) }

    var selectedTodo by remember { mutableStateOf<Todo?>(null) }  // Holds the selected todo for editing

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Todos (Room Database)",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Bottombar.AddTodoScreen.route) },
                containerColor = Color(0xFF6200EA)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo", tint = Color.White)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            if (alltodos.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(alltodos.size) { index ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = alltodos[index].title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = alltodos[index].description,
                                        fontSize = 14.sp,
                                        color = Color(0xFF666666)
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable {
                                                selectedTodo = alltodos[index] // Set the selected todo
                                            }
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFFE53935),
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable {
                                                viewModel.deleteTodo(alltodos[index])
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Todos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }


        }

    }

    // todo Show edit dialog when a todo is selected
    selectedTodo?.let { todo ->
        EditTodoDialog(
            todo = todo,
            onDismiss = { selectedTodo = null },  // Close dialog
            viewModel = viewModel
        )
    }
}
@Composable
fun EditTodoDialog(
    todo: Todo,
    onDismiss: () -> Unit,
    viewModel: TodoViewModel
) {
    var title by remember { mutableStateOf(TextFieldValue(todo.title)) }
    var description by remember { mutableStateOf(TextFieldValue(todo.description)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Todo") },
        text = {
            Column {
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.text.isNotBlank() && description.text.isNotBlank()) {
                        val updatedTodo = todo.copy(title = title.text, description = description.text)
                        viewModel.updateTodo(updatedTodo)
                        onDismiss()
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


