package com.example.todoapp.add_task.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey val id: Long,
    val task: String,
    val done: Boolean = false
)
