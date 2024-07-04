package com.example.todoapp.add_task.domain

import com.example.todoapp.add_task.data.TaskRepository
import com.example.todoapp.add_task.ui.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {


    suspend operator fun invoke(taskModel: TaskModel) {
        repository.update(taskModel)
    }

}