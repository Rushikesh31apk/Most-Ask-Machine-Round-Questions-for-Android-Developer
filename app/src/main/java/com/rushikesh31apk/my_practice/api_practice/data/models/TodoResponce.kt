package com.rushikesh31apk.my_practice.api_practice.data.models

// 1. Todo Data Model
data class TodoResponce(
    val id: Int,
    val title: String,
    val description: String,
    val completed: Boolean,
    val created_at: String
)
