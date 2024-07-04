package com.example.todoapp.add_task.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.add_task.ui.model.TaskModel
import javax.inject.Inject

class TaskViewModel
@Inject constructor(

) : ViewModel() {

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    fun closeDialog() {
        _showDialog.value = false
    }

    fun showDialog() {
        _showDialog.value = true
    }

    fun createTask(task: String) {
        Log.i("ToDoApp", task)
        _tasks.add(TaskModel(task = task))
        _showDialog.value = false
    }

    fun onTaskDone(task: TaskModel) {
        val index = tasks.indexOf(task)
        _tasks[index] = _tasks[index].let {
            it.copy(done = !it.done)
        }
    }

    fun onTaskRemove(task: TaskModel) {
        val selectedTask = _tasks.find { it.id == task.id }
        _tasks.remove(selectedTask)
    }

}