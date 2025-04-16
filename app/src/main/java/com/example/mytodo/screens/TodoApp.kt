package com.example.mytodo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mytodo.TodoItem
import com.example.mytodo.viewmodel.TodoViewModel

@Composable
fun TodoApp(viewModel: TodoViewModel) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ){
        Column {
            AddTask(viewModel)
            Spacer(modifier = Modifier.height(8.dp))
            TodoView(viewModel)
        }
    }
}

@Composable
fun TodoView(viewModel: TodoViewModel){
    val todos by viewModel.todos.collectAsState()
    LazyColumn{
        items(todos) { todo ->
            TodoItem(
                viewModel,
                todo = todo,
                onToggleDone = { viewModel.toggleDone(todo) },
                onToggleEdit = {viewModel.toggleEdit(todo)},
                onDelete = { viewModel.deleteTodo(todo) }
            )
        }
    }
}


@Composable
fun AddTask(viewModel: TodoViewModel){
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        PopUpForm (
            onDismiss = { showDialog = false },
            name = "",
            desc = "",
            onAddTask = { name, description ->
                viewModel.addTodo(name, description)
            }
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)) {
        Button(onClick = {showDialog = true},
            modifier = Modifier
                .fillMaxWidth(1f)
        ){
            Text("Add Task")
        }
    }
}


@Composable
fun PopUpForm(
    onDismiss: () -> Unit,
    name: String,
    desc: String,
    onAddTask: (String, String) -> Unit
) {
    var taskName by remember { mutableStateOf(name) }
    var taskDesc by remember { mutableStateOf(desc) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add New Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    label = { Text("Task Name") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = taskDesc,
                    onValueChange = { taskDesc = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddTask(taskName, taskDesc)
                    onDismiss()
                },
                enabled = taskName.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


