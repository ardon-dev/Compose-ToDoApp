package com.example.todoapp.add_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.add_task.domain.AddTaskUseCase
import com.example.todoapp.add_task.domain.GetTaskUseCase
import com.example.todoapp.add_task.ui.TasksUIState.Success
import com.example.todoapp.add_task.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel
@Inject constructor(
    getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
) : ViewModel() {

    val uiState: StateFlow<TasksUIState> = getTaskUseCase()
        .map(::Success)
        .catch { TasksUIState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUIState.Loading)

    //private val _tasks = mutableStateListOf<TaskModel>()
    //val tasks: List<TaskModel> = _tasks

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    fun closeDialog() {
        _showDialog.value = false
    }

    fun showDialog() {
        _showDialog.value = true
    }

    fun createTask(task: String) {
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onTaskDone(task: TaskModel) {
        //val index = tasks.indexOf(task)
        //_tasks[index] = _tasks[index].let {
        //    it.copy(done = !it.done)
        //}
    }

    fun onTaskRemove(task: TaskModel) {
        //val selectedTask = _tasks.find { it.id == task.id }
        //_tasks.remove(selectedTask)
    }

}