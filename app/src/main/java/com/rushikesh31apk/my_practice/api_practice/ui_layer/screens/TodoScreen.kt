package com.rushikesh31apk.my_practice.api_practice.ui_layer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rushikesh31apk.my_practice.api_practice.data.models.TodoResponce
import com.rushikesh31apk.my_practice.api_practice.ui_layer.TodoViewModel

@Composable
fun TodoScreen() {
    val viewModel = viewModel<TodoViewModel>()
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val showDialog by viewModel.showAddDialog.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTodos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        // Title and Add button row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Todo List",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(
                onClick = { viewModel.showAddTodoDialog() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text("Add")

            }
        }

        // Error message
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

        }


        // Loading indicator
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Todo list
        LazyColumn {
            items(todos) { todo ->
                TodoItem(
                    viewModel = viewModel,
                    todo = todo,
                    onToggleComplete = { viewModel.toggleTodoComplete(it) },
                    onDelete = { viewModel.deleteTodo(it.id) }
                )
            }
        }
    }

    // Add Todo Dialog
    if (showDialog) {
        AddTodoDialog(
            onDismiss = { viewModel.hideAddTodoDialog() },
            onConfirm = { title, description ->
                viewModel.createTodo(title, description)
            }
        )
    }
}

@Composable
fun TodoItem(
    todo: TodoResponce,
    onToggleComplete: (TodoResponce) -> Unit,
    onDelete: (TodoResponce) -> Unit,
    viewModel: TodoViewModel
) {
    val showDialog by viewModel.showUpdateDialog.collectAsState()
    val selectedTodo by viewModel.selectedTodo.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onToggleComplete(todo.copy()) }) {
                Icon(
                    imageVector = if (todo.completed) Icons.Default.CheckBoxOutlineBlank else Icons.Default.CheckBox,
                    contentDescription = "Toggle complete",
                    tint = if (todo.completed) Color.Red else Color.Green
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (todo.completed) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Row {
                IconButton(onClick = { viewModel.showUpdateTodoDialog(todo) }) {
                    Icon(
                        imageVector = Icons.Default.ModeEdit,
                        contentDescription = "Toggle complete",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { onDelete(todo) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
    if (showDialog && selectedTodo != null) {
        UpdateTodo(
            todo = selectedTodo!!,
            onDismiss = { viewModel.hideUpdateTodoDialog() },
            onConfirm = { updatedTodo ->
                viewModel.updateTodo(updatedTodo)
            }
        )
    }

}

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add New Todo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    isError = titleError,
                    supportingText = if (titleError) {
                        { Text("Title is required") }
                    } else null
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    minLines = 3
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onConfirm(title, description)
                            } else {
                                titleError = true
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateTodo(
    todo: TodoResponce,
    onDismiss: () -> Unit,
    onConfirm: (TodoResponce) -> Unit
) {
    var title by remember { mutableStateOf(todo.title) }
    var description by remember { mutableStateOf(todo.description) }
    var titleError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Update Todo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    isError = titleError,
                    //readOnly = true // Prevent title from being editable (fixes click issue)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    minLines = 3,
//readOnly = true // Prevent description from being editable
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                // Update only the time (assuming updatedAt is a field)
                                val updatedTodo = todo.copy(title = title, description = description)
                                onConfirm(updatedTodo)
                            } else {
                                titleError = true
                            }
                        }
                    ) {
                        Text("Update Time")
                    }
                }
            }
        }
    }
}


