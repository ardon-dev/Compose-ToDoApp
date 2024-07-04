package com.example.todoapp.add_task.ui

import com.example.todoapp.add_task.ui.model.TaskModel

sealed interface TasksUIState {

    object Loading: TasksUIState
    data class Error(val throwable: Throwable)
    data class Success(val tasks: List<TaskModel>) : TasksUIState

}