package com.example.todoapp.add_task.ui.model

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    val done: Boolean = false
)
