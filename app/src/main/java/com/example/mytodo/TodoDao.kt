package com.example.mytodo

import androidx.annotation.RequiresPermission
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo ORDER BY id DESC")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

//    @Query("SELECT * FROM Todo WHERE id =:id LIMIT 1")
//    suspend fun get(id: Int): Flow<Todo?>

    @Delete
    suspend fun delete(todo: Todo)
}