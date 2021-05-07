package com.example.todoappmvp.data.source

import com.example.todoappmvp.data.Task
import com.example.todoappmvp.data.source.remote.TasksRemoteDataSource

class TasksRepository(
    private val tasksRemoteDataSource: TasksRemoteDataSource
) : TasksDataSource {

    private var cachedTasks: Map<String, Task> = mapOf()
    private var cacheIsDirty = false

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        if (cacheIsDirty.not()) {
            callback.onTasksLoaded(cachedTasks.values.toList())
            return
        }
        if (cacheIsDirty) {

        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        TODO("Not yet implemented")
    }

    override fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }

    override fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback) {
        tasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback() {
            override fun onTasksLoaded(tasks: List<Task>) {
                refreshTasks(tasks)

            }

            override fun onDataNotAvailable() {
                TODO("Not yet implemented")
            }
        })
    }

    private fun refreshLocalDataSource(tasks: List<Task>) {

    }
}
