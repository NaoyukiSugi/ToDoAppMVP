package com.example.todoappmvp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoappmvp.data.Task

@Database(entities = [Task::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "Tasks.db"

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME
                ).build()
                    .also { INSTANCE = it }
            }
    }
}
