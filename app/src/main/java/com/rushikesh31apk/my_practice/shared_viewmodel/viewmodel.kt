package com.rushikesh31apk.my_practice.shared_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

// 1. First, create a shared ViewModel
class SharedViewModel : ViewModel() {
    // MutableStateFlow for real-time updates
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()

    // For large data sets
    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error handling
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun updateUserData(user: UserData) {
        viewModelScope.launch {
            _userData.emit(user)
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                _isLoading.emit(true)
                // Simulate API call or database operation
                delay(1000)
                _productList.emit(generateSampleProducts())
                _isLoading.emit(false)
            } catch (e: Exception) {
                _error.emit(e.message)
                _isLoading.emit(false)
            }
        }
    }

    private fun generateSampleProducts(): List<Product> {
        return List(100) { index ->
            Product(
                id = index,
                name = "Product $index",
                price = Random.nextDouble(10.0, 100.0)
            )
        }
    }
}