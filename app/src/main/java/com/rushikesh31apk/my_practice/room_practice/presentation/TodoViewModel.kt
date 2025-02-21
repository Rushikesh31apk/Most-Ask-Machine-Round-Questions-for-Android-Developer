package com.rushikesh31apk.my_practice.room_practice.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rushikesh31apk.my_practice.room_practice.data.repository.Repo
import com.rushikesh31apk.my_practice.room_practice.data.TodoDatabase
import com.rushikesh31apk.my_practice.room_practice.data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = TodoDatabase.getDatabase(application) // instance
    private val repository = Repo(db.dao) // Repo instance

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            repository.todos.collect { _todos.value = it }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodoById(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertTodo(todo)
        }
    }


}