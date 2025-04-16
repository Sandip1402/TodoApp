package com.example.mytodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mytodo.Todo
import com.example.mytodo.TodoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val dao: TodoDao) : ViewModel() {
    val todos = dao.getAllTodos().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addTodo(task: String, desc: String) {
        viewModelScope.launch {
            dao.insert(Todo(task = task, desc = desc))
        }
    }

    fun toggleDone(todo: Todo) {
        viewModelScope.launch {
            dao.update(todo.copy(isDone = !todo.isDone))
        }
    }

    fun editDone(todo: Todo) {
        viewModelScope.launch{
            dao.update(todo)
        }
    }

    fun toggleEdit(todo: Todo) {
        viewModelScope.launch {
            dao.update(todo.copy(edit = !todo.edit))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            dao.delete(todo)
        }
    }
}