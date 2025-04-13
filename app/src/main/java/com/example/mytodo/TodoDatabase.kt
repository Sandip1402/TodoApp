package com.example.mytodo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile //added this
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) { //changed this
                val instance = Room.databaseBuilder( //changed this
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"
                ).build() //changed this
                INSTANCE = instance //changed this
                instance //changed this
            }
        }
    }
}
