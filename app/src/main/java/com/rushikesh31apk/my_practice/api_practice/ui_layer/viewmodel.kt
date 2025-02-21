package com.rushikesh31apk.my_practice.api_practice.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rushikesh31apk.my_practice.api_practice.data.models.NewTodoInput
import com.rushikesh31apk.my_practice.api_practice.data.models.TodoResponce
import com.rushikesh31apk.my_practice.api_practice.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 4. ViewModel
class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoResponce>>(emptyList())
    val todos: StateFlow<List<TodoResponce>> = _todos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    fun loadTodos() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _todos.value = RetrofitClient.api.getAllTodos()
            } catch (e: Exception) {
                _error.value = "Failed to load todos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleTodoComplete(todo: TodoResponce) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                RetrofitClient.api.updateTodo(todo.id, todo.copy(completed = !todo.completed))
                loadTodos()
            } catch (e: Exception) {
                _error.value = "Failed to update todo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateTodo(todo: TodoResponce) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                RetrofitClient.api.updateTodo(todo.id, todo)
                hideUpdateTodoDialog()
                loadTodos()
            } catch (e: Exception) {
                _error.value = "Failed to update todo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = RetrofitClient.api.deleteTodo(id)
                if (response.isSuccessful) {
                    loadTodos() // Refresh the list after successful deletion
                } else {
                    _error.value = "Failed to delete todo: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Failed to delete todo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog.asStateFlow()

    private val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog: StateFlow<Boolean> = _showUpdateDialog.asStateFlow()

    private val _selectedTodo = MutableStateFlow<TodoResponce?>(null)
    val selectedTodo: StateFlow<TodoResponce?> = _selectedTodo

    fun showUpdateTodoDialog(todo: TodoResponce) {
        _selectedTodo.value = todo
        _showUpdateDialog.value = true
    }

    fun hideUpdateTodoDialog() {
        _selectedTodo.value = null
        _showUpdateDialog.value = false
    }

    fun showAddTodoDialog() {
        _showAddDialog.value = true
    }

    fun hideAddTodoDialog() {
        _showAddDialog.value = false
    }

    fun createTodo(title: String, description: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val newTodo = NewTodoInput(title, description)
                RetrofitClient.api.createTodo(newTodo)
                loadTodos()
                hideAddTodoDialog()
            } catch (e: Exception) {
                _error.value = "Failed to create todo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}