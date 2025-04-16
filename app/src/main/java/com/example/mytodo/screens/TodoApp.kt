package com.example.mytodo

import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoApp(viewModel: TodoViewModel) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ){
        Column {
            Text("My Todo",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                todo = todo,
                onToggleDone = { viewModel.toggleDone(todo) },
                onEdit = { viewModel.editTodo(todo) },
                onDelete = { viewModel.deleteTodo(todo) }
            )
        }
    }
}


@Composable
fun AddTask(viewModel: TodoViewModel){
    var taskName by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)) {
        Button(onClick = ()){

        }
    }
}



