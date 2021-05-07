package com.example.todoappmvp.data.source.local

import com.example.todoappmvp.data.Task
import com.example.todoappmvp.data.source.TasksDataSource
import com.example.todoappmvp.util.AppExecutors

class TasksLocalDataSource(
    private val appExecutors: AppExecutors,
    private val taskDao: TaskDao
) : TasksDataSource {
    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val tasks = taskDao.getTasks()

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
        val itr = TASKS_SERVICE_DATA.iterator()
        while (itr.hasNext()) {
            itr.hasNext()
        }


//        val it: MutableIterator<Map.Entry<String, Task>> =
//            com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource.TASKS_SERVICE_DATA.entries.iterator()
//        while (it.hasNext()) {
//            val entry = it.next()
//            if (entry.value.isCompleted()) {
//                it.remove()
//            }
//        }
    }

    override fun refreshTasks() {
        // do nothing
    }

    override fun deleteAllTasks() {
    }

    override fun deleteTask(taskId: String) {
        object : Runnable {
            override fun run() {

            }

        }
    }

    private companion object {
        @Volatile
        private var INSTANCE: TasksLocalDataSource? = null

        private val TASKS_SERVICE_DATA = LinkedHashSet<Int>(2)

        fun getInstance(appExecutor: AppExecutors, taskDao: TaskDao) {
            INSTANCE ?: synchronized(this) {
                TasksLocalDataSource(appExecutor, taskDao)
            }
        }
    }
}
