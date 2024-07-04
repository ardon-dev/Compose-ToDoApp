package com.example.todoapp.add_task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoapp.add_task.ui.model.TaskModel

@Preview
@Composable
fun TaskScreenPreview() {
    //TaskScreen(Modifier, TaskViewModel())
}

@Composable
fun TaskScreen(
    modifier: Modifier,
    viewModel: TaskViewModel,
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val showDialog: Boolean by viewModel.showDialog.observeAsState(false)

    val uiState by produceState<TasksUIState>(
        initialValue = TasksUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        TasksUIState.Loading -> {
            CircularProgressIndicator()
        }
        is TasksUIState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                AddTaskDialog(
                    show = showDialog,
                    onTaskAdded = { task ->
                        viewModel.createTask(task)
                    },
                    onDismiss = {
                        viewModel.closeDialog()
                    }
                )
                TaskFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    onClick = {
                        viewModel.showDialog()
                    }
                )
                TaskList((uiState as TasksUIState.Success).tasks, viewModel)
            }
        }
    }

}

@Composable
fun TaskList(tasks: List<TaskModel>, viewModel: TaskViewModel) {
    LazyColumn {
        items(
            items = tasks,
            key = { it.id }
        ) { taskItem ->
            TaskItem(
                task = taskItem,
                onDoneSelected = {
                    viewModel.onTaskDone(taskItem)
                },
                onRemove = {
                    viewModel.onTaskRemove(taskItem)
                }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: TaskModel,
    onDoneSelected: () -> Unit,
    onRemove: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onRemove()
                    }
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = task.task,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            Checkbox(
                checked = task.done,
                onCheckedChange = {
                    onDoneSelected()
                }
            )
        }
    }
}

@Composable
fun TaskFab(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add task"
        )
    }
}

@Composable
fun AddTaskDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onTaskAdded: (String) -> Unit,
) {

    var task by remember {
        mutableStateOf("")
    }

    if (show) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp)
            ) {

                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.size(16.dp))
                TextField(
                    value = task,
                    onValueChange = {
                        task = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(Modifier.size(16.dp))
                Button(
                    onClick = {
                        onTaskAdded(task)
                        task = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir")
                }

            }
        }
    }
}