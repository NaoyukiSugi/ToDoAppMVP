package com.example.todoappmvp.data.source.local

import com.example.todoappmvp.data.Task
import com.example.todoappmvp.data.source.TasksDataSource
import com.example.todoappmvp.util.AppExecutors
import kotlinx.coroutines.Runnable

class TasksLocalDataSource(
        private val appExecutors: AppExecutors,
        private val tasksDao: TaskDao
) : TasksDataSource {
    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        val runnable = Runnable {
            val tasks = tasksDao.getTasks()
            appExecutors.mainThread.execute {
                if (tasks.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onTasksLoaded(tasks)
                }
            }
        }
        appExecutors.diskIO.execute(runnable)
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val runnable = Runnable {
            val task = tasksDao.getTaskById(taskId)
            appExecutors.mainThread.execute {
                if (task != null) {
                    callback.onTaskLoaded(task)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
        appExecutors.diskIO.execute(runnable)
    }

    override fun saveTask(task: Task) {
        checkNotNull(task)
        val saveRunnable = Runnable {
            tasksDao.insertTask(task)
        }
        appExecutors.diskIO.execute(saveRunnable)
    }

    override fun completeTask(task: Task) {
        val completeRunnable = Runnable {
            tasksDao.updateCompleted(task.id, true)
        }
        appExecutors.diskIO.execute(completeRunnable)
    }

    override fun completeTask(taskId: String) {
        // do nothing
    }

    override fun activateTask(task: Task) {
        val activateRunnable = Runnable {
            tasksDao.updateCompleted(task.id, false)
        }
        appExecutors.diskIO.execute(activateRunnable)
    }

    override fun activateTask(taskId: String) {
        // do nothing
    }

    override fun clearCompletedTasks() {
        val clearTasksRunnable = Runnable {
            tasksDao.deleteCompletedTasks()
        }
        appExecutors.diskIO.execute(clearTasksRunnable)
    }

    override fun refreshTasks() {
        // do nothing
    }

    override fun deleteAllTasks() {
        val deleteRunnable = Runnable {
            tasksDao.deleteTasks()
        }
        appExecutors.diskIO.execute(deleteRunnable)
    }

    override fun deleteTask(taskId: String) {
        val deleteRunnable = Runnable {
            tasksDao.deleteTaskById(taskId)
        }
        appExecutors.diskIO.execute(deleteRunnable)
    }

    private companion object {
        @Volatile
        private var INSTANCE: TasksLocalDataSource? = null

        fun getInstance(appExecutor: AppExecutors, taskDao: TaskDao) {
            INSTANCE ?: synchronized(this) {
                TasksLocalDataSource(appExecutor, taskDao)
            }
        }
    }
}
