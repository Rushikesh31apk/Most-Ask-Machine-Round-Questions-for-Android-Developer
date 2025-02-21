package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rushikesh31apk.my_practice.shared_viewmodel.SharedViewModel

// 4. Products Screen - Handling large datasets
@Composable
fun ProductsScreen(sharedViewModel: SharedViewModel) {
    val products by sharedViewModel.productList.collectAsState()
    val isLoading by sharedViewModel.isLoading.collectAsState()
    val error by sharedViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error")
            else -> LazyColumn {
                items(products) { product ->
                    ProductItem(product)
                }
            }
        }
    }
}