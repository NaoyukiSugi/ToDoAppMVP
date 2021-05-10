package com.example.todoappmvp.tasksdetail

import com.example.todoappmvp.BasePresenter
import com.example.todoappmvp.BaseView

interface TaskDetailContract {

    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active: Boolean)
        fun showMissingTask()
        fun hideTitle()
        fun showTitle(title: String)
        fun hideDescription()
        fun showDescription(description: String)
        fun showCompletionStatus(complete: Boolean)
        fun showEditTask(taskId: String)
        fun showTaskDeleted()
        fun showTaskMarkedComplete()
        fun showTaskMarkedActive()
        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {
        fun editTask()
        fun deleteTask()
        fun completeTask()
        fun activateTask()
    }
}
