package com.rushikesh31apk.my_practice.shared_viewmodel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rushikesh31apk.my_practice.shared_viewmodel.Product

// 5. Product item composable
@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Name: ${product.name}")
            Text("Price: $${String.format("%.2f", product.price)}")
        }
    }
}