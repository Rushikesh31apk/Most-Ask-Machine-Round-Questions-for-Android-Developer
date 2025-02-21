package com.rushikesh31apk.my_practice.room_practice.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)