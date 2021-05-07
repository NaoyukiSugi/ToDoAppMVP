package com.example.todoappmvp.data.source

import android.os.Handler
import com.example.todoappmvp.data.Task

class TasksRemoteDataSource(
    private val tasksServiceData: LinkedHashMap<String, Task> = linkedMapOf(
        "1000" to Task(
            id = "1000",
            title = "Build tower in Pisa",
            description = "Ground looks good, no foundation work required."
        ),
        "2000" to Task(
            id = "2000",
            title = "Finish bridge in Tacoma",
            description = "Finish bridge in Tacoma"
        )
    )
) : TasksDataSource {

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        Handler().postDelayed(
            { callback.onTasksLoaded(tasksServiceData.values.toList()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task = tasksServiceData[taskId]
        if (task != null) {
            Handler().postDelayed(
                { callback.onTaskLoaded(task) },
                SERVICE_LATENCY_IN_MILLIS
            )
        }
    }

    override fun saveTask(task: Task) {
        tasksServiceData[task.id] = task
    }

    override fun completeTask(task: Task) {
        val completedTask =
            Task(
                id = task.id,
                title = task.title,
                description = task.description,
                completed = true
            )
        tasksServiceData[task.id] = completedTask
    }

    override fun completeTask(taskId: String) {
        // do Nothing
    }

    override fun activateTask(task: Task) {
        val completedTask =
            Task(
                id = task.id,
                title = task.title,
                description = task.description,
                completed = false
            )
        tasksServiceData[task.id] = completedTask
    }

    override fun activateTask(taskId: String) {
        // do nothing
    }

    override fun clearCompletedTasks() {
        val it = tasksServiceData.iterator()
        while (it.hasNext()) {
            val entry: Map.Entry<String, Task> = it.next()
            if (entry.value.completed) {
                it.remove()
            }
        }
    }

    override fun refreshTasks() {
        // do nothing
    }

    override fun deleteAllTasks() {
        tasksServiceData.clear()
    }

    override fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
    }

    private companion object {
        @Volatile
        var INSTANCE: TasksRemoteDataSource? = null

        const val SERVICE_LATENCY_IN_MILLIS = 5000L

        fun getInstance() {
            INSTANCE ?: synchronized(this) { TasksRemoteDataSource() }
        }
    }
}
