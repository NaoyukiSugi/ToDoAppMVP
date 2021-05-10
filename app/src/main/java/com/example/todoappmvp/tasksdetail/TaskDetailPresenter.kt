package com.example.todoappmvp.tasksdetail

import com.example.todoappmvp.data.Task
import com.example.todoappmvp.data.source.TasksDataSource
import com.example.todoappmvp.data.source.TasksRepository
import com.google.common.base.Strings

class TaskDetailPresenter(
    private var tasksRepository: TasksRepository,
    private var taskDetailView: TaskDetailContract.View,
    private val taskId: String
) : TaskDetailContract.Presenter {

    override fun editTask() {
        TODO("Not yet implemented")
    }

    override fun deleteTask() {
        TODO("Not yet implemented")
    }

    override fun completeTask() {
        TODO("Not yet implemented")
    }

    override fun activateTask() {
        TODO("Not yet implemented")
    }

    override fun start() {

    }

    private fun openTask() {
        if (Strings.isNullOrEmpty(taskId)) {
            taskDetailView.showMissingTask()
            return
        }

        taskDetailView.setLoadingIndicator(true)
        tasksRepository.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                if (taskDetailView.isActive()) {
                    return
                }
                taskDetailView.setLoadingIndicator(false)
                if (null == task) {
                    taskDetailView.showMissingTask()
                } else {
                    showTask(task)
                }
            }

            override fun onDataNotAvailable() {
                if (taskDetailView.isActive()) {
                    return
                }
                taskDetailView.showMissingTask()
            }
        })
    }

    private fun showTask(task: Task) {
        val title: String = task.title
        val description = task.description

        if (Strings.isNullOrEmpty(title)) {
            taskDetailView.hideTitle()
        } else {
            taskDetailView.showTitle()
        }

        if (Strings.isNullOrEmpty(description)) {
            taskDetailView.hideDescription()
        } else {
            taskDetailView.showDescription(description)
        }
        taskDetailView.showCompletionStatus(task.completed)
    }

}
