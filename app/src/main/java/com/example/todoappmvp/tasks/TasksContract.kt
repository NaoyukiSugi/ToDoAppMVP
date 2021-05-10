package com.example.todoappmvp.tasks

import com.example.todoappmvp.BasePresenter
import com.example.todoappmvp.BaseView
import com.example.todoappmvp.data.Task

interface TasksContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showTasks(tasks: List<Task>)

        fun showAddTask()

        fun showTaskDetailsUi(taskId: String)

        fun showTaskMarkedComplete()

        fun showTaskMarkedActive()

        fun showCompletedTasksCleared()

        fun showLoadingTasksError()

        fun showNoTasks()

        fun showActiveFilterLabel()

        fun showCompletedFilterLabel()

        fun showAllFilterLabel()

        fun showNoActiveTasks()

        fun showNoCompletedTasks()

        fun showSuccessfullySavedMessage()

        fun isActive(): Boolean

        fun showFilteringPopUpMenu()
    }

    interface Presenter : BasePresenter {
        fun result(requestCode: Int, resultCode: Int)

        fun loadTasks(forceUpdate: Boolean)

        fun addNewTask()

        fun openTaskDetails(requestedTask: Task)

        fun completeTask(completedTask: Task)

        fun activateTask(activeTask: Task)

        fun clearCompletedTasks()

        fun setFiltering(requestType: TasksFilterType)

        fun getFiltering(): TasksFilterType
    }
}
