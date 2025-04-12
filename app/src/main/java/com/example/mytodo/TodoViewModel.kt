package com.example.mytodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val dao: TodoDao) : ViewModel() {
    val todos = dao.getAllTodos().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addTodo(task: String) {
        viewModelScope.launch {
            dao.insert(Todo(task = task))
        }
    }

    fun toggleDone(todo: Todo) {
        viewModelScope.launch {
            dao.update(todo.copy(isDone = !todo.isDone))
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            dao.delete(todo)
        }
    }
}