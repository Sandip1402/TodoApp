package com.example.mytodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.mytodo.screens.TodoApp
import com.example.mytodo.viewmodel.TodoViewModel
import com.example.mytodo.viewmodel.TodoViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Create the ViewModel scoped to this Activity
        val db = TodoDatabase.getDatabase(applicationContext)
        val dao = db.todoDao()
        val viewModelFactory = TodoViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]


        // Set content using Compose
        setContent {
            TodoApp(viewModel)
        }
    }
}





