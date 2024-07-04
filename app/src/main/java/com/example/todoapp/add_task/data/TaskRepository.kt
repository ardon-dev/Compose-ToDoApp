package com.example.todoapp.add_task.data

import com.example.todoapp.add_task.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map { items ->
        items.map {
            TaskModel(it.id, it.task, it.done)
        }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(TaskEntity(
            id = taskModel.id,
            task = taskModel.task,
            done = taskModel.done
        ))
    }

}