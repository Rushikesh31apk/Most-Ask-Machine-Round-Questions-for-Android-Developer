package com.rushikesh31apk.my_practice.marsApi.ui_layer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rushikesh31apk.my_practice.marsApi.data_layer.models.MarsPhoto

@Composable
fun MarsUiScreen( marsViewModel : MarsViewModel = viewModel()) {

    val marsUiState: List<MarsPhoto> = marsViewModel.marsUiState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (marsUiState.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Downloading, contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = "Loading")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                item {
                    Text(text = "Mars Photos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                items(marsUiState.size) {index ->
                    MarsPhotoCard(marsPhoto = marsUiState[index], modifier = Modifier.weight(1f))
                }
            }
        }
    }

}

@Composable
fun MarsPhotoCard(marsPhoto: MarsPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = marsPhoto.img_src,
                contentDescription = "Mars Photo",
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
