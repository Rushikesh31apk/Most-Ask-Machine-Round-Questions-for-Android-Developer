package com.rushikesh31apk.my_practice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rushikesh31apk.my_practice.marsApi.ui_layer.MarsUiScreen
import com.rushikesh31apk.my_practice.ui.theme.My_practiceTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.rushikesh31apk.my_practice.AppNavigation.AppNavHost


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            My_practiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(navController)
                }
            }
        }
    }
}