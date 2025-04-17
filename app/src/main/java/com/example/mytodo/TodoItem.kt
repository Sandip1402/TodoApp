package com.example.mytodo


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodo.viewmodel.TodoViewModel


@Composable
fun TodoItem(viewModel: TodoViewModel, todo: Todo, onToggleDone: () -> Unit, onToggleEdit: () -> Unit, onDelete: () -> Unit) {
    var showDialog = remember{ mutableStateOf(false)}
    if(showDialog.value)
        TaskDetail(todo, {showDialog.value = false})

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color.LightGray)
            .clickable { showDialog.value = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = todo.isDone, onCheckedChange = { onToggleDone() })
        Text(
            text = todo.task,
            modifier = Modifier.weight(1f),
            textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
        )
        IconButton(onClick = { onToggleEdit() }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
            if(todo.edit){
                PopUpForm(viewModel, todo)
            }
        }
        IconButton(onClick = { onDelete()} ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun PopUpForm(viewModel: TodoViewModel, todo: Todo) {
    var taskName by remember { mutableStateOf(todo.task) }
    var taskDesc by remember { mutableStateOf(todo.desc) }
    AlertDialog(
        onDismissRequest = { viewModel.toggleEdit(todo) },
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
                    viewModel.editDone(Todo(todo.id,taskName, taskDesc))
                },
                enabled = taskName.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = {viewModel.toggleEdit(todo)}) {
                Text("Cancel")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetail(todo: Todo, dismiss: () -> Unit){
    BasicAlertDialog(
        onDismissRequest = dismiss,
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp,10.dp)
                .fillMaxWidth(1f)
                .background(color = Color.White)
        ){
            Text(todo.task, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(todo.desc)
        }
    }
}


