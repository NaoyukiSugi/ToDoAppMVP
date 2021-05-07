package com.example.todoappmvp.data.source.local

import androidx.room.*
import com.example.todoappmvp.data.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE entryid = :taskId")
    fun getTaskById(taskId: String): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetTask(task: Task)

    @Update
    fun updateTask(task: Task): Int

    @Query("UPDATE tasks SET completed = :completed WHERE entryid = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM tasks WHERE entryid = :taskId")
    fun deleteTaskById(taskId: String)

    @Query("DELETE FROM tasks")
    fun deleteTasks()

    @Query("DELETE FROM tasks WHERE completed = 1")
    fun deleteCompletedTasks()
}
