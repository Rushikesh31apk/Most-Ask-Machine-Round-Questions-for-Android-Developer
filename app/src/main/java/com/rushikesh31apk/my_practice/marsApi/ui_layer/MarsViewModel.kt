package com.rushikesh31apk.my_practice.marsApi.ui_layer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rushikesh31apk.my_practice.marsApi.data_layer.ApiInstance
import com.rushikesh31apk.my_practice.marsApi.data_layer.models.MarsPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarsViewModel : ViewModel() {
    private val _marsUiState = MutableStateFlow<List<MarsPhoto>>(emptyList())
    val marsUiState: StateFlow<List<MarsPhoto>> = _marsUiState

    private var currentPage = 1
    private val pageSize = 10
    private var isLoading = false

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val response = ApiInstance.api.getPhotos(page = currentPage, limit = pageSize)
                _marsUiState.value = _marsUiState.value + response
                currentPage++ // Move to the next page
            } catch (e: Exception) {
                Log.d("Error", "Failed to fetch photos: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
