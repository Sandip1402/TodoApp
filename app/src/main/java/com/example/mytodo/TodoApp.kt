package com.example.mytodo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TodoApp(viewModel: TodoViewModel) {
    val todos by viewModel.todos.collectAsState()

    var newTask by remember { mutableStateOf("") }

    Scaffold(
        topBar = {Text("To-Do App")}
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
//            .padding(16.dp)
            .fillMaxSize()) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Add task") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button( {
                    if (newTask.isNotBlank()) {
                        viewModel.addTodo(newTask)
                        newTask = ""
                    }
                }) {
                    Text("Add")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onToggleDone = { viewModel.toggleDone(todo) },
                        onDelete = { viewModel.deleteTodo(todo) }
                    )
                }
            }
        }
    }
}




