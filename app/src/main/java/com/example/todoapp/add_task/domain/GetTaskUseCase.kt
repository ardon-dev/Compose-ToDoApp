package com.example.todoapp.add_task.domain

import com.example.todoapp.add_task.data.TaskRepository
import com.example.todoapp.add_task.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(): Flow<List<TaskModel>> = repository.tasks

}