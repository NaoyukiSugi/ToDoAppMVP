package com.example.todoappmvp.data.source

import com.example.todoappmvp.data.Task

class TasksRepository(
    private val tasksRemoteDataSource: TasksDataSource,
    private val tasksLocalDataSource: TasksDataSource
) : TasksDataSource {

    private var cachedTasks: MutableMap<String, Task> = mutableMapOf()
    private var cacheIsDirty = false

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        checkNotNull(callback)
        if (cachedTasks != null && !cacheIsDirty) {
            callback.onTasksLoaded(cachedTasks.values.toList())
            return
        }
        if (cacheIsDirty) {
            getTasksFromRemoteDataSource(callback)
        } else {
            tasksLocalDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
                override fun onTasksLoaded(tasks: List<Task>) {
                    refreshCache(tasks)
                    callback.onTasksLoaded(cachedTasks.values.toList())
                }

                override fun onDataNotAvailable() {
                    getTasksFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        checkNotNull(taskId)
        checkNotNull(callback)

        val cachedTask = getTaskWithId(taskId)
        if (cachedTask != null) {
            callback.onTaskLoaded(cachedTask)
            return
        }

        tasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                if (cachedTasks == null) {
                    cachedTasks = linkedMapOf()
                }
                cachedTasks[task.id] = task
                callback.onTaskLoaded(task)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun saveTask(task: Task) {
        checkNotNull(task)
        tasksRemoteDataSource.saveTask(task)
        tasksLocalDataSource.saveTask(task)

        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        cachedTasks[task.id] = task
    }

    override fun completeTask(task: Task) {
        checkNotNull(task)
        tasksRemoteDataSource.completeTask(task)
        tasksLocalDataSource.completeTask(task)

        val completedTask = Task(task.title, task.description, task.id, true)

        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        cachedTasks[task.id] = completedTask
    }

    override fun completeTask(taskId: String) {
        getTaskWithId(taskId)?.let { completeTask(it) }
    }

    override fun activateTask(task: Task) {
        checkNotNull(task)
        tasksRemoteDataSource.activateTask(task)
        tasksLocalDataSource.activateTask(task)

        val activeTask = Task(task.title, task.description, task.id)

        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        cachedTasks[task.id] = activeTask
    }

    override fun activateTask(taskId: String) {
        getTaskWithId(taskId)?.let { activateTask(it) }
    }

    override fun clearCompletedTasks() {
        tasksRemoteDataSource.clearCompletedTasks()
        tasksLocalDataSource.clearCompletedTasks()

        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        val it = cachedTasks.entries.iterator()
        while (it.hasNext()) {
            val entry = it.next()
            if (entry.value.completed) {
                it.remove()
            }
        }
    }

    override fun refreshTasks() {
        cacheIsDirty = true
    }

    override fun deleteAllTasks() {
        tasksRemoteDataSource.deleteAllTasks()
        tasksLocalDataSource.deleteAllTasks()

        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        cachedTasks.clear()
    }

    override fun deleteTask(taskId: String) {
        tasksRemoteDataSource.deleteTask(taskId)
        tasksLocalDataSource.deleteTask(taskId)

        cachedTasks.remove(taskId)
    }

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback) {
        tasksRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                refreshCache(tasks)
                refreshLocalDataSource(tasks)
                callback.onTasksLoaded(cachedTasks.values.toList())
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(tasks: List<Task>) {
        if (cachedTasks == null) {
            cachedTasks = linkedMapOf()
        }
        cachedTasks.clear()
        tasks.forEach { task ->
            cachedTasks[task.id] = task
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(tasks: List<Task>) {
        tasksLocalDataSource.deleteAllTasks()
        tasks.forEach { task ->
            tasksLocalDataSource.saveTask(task)
        }
    }

    private fun getTaskWithId(id: String): Task? {
        if (cachedTasks == null || cachedTasks.isEmpty()) {
            return null
        } else {
            return cachedTasks[id]
        }

    }

    private companion object {
        @Volatile
        private var INSTANCE: TasksRepository? = null

        fun getInstance(
            tasksRemoteDataSource: TasksDataSource,
            tasksLocalDataSource: TasksDataSource
        ) {
            INSTANCE ?: synchronized(this) {
                TasksRepository(tasksRemoteDataSource, tasksLocalDataSource)
            }
        }
    }
}
