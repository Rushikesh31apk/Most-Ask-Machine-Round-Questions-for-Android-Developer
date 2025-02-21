package com.rushikesh31apk.my_practice.api_practice.data.models

// Add this data class for new todo input
data class NewTodoInput(
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
)