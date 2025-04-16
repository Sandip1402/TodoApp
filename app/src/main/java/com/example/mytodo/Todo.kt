package com.example.mytodo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
// schema of table
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val desc: String,
    val isDone: Boolean = false,
    val edit: Boolean = false
)