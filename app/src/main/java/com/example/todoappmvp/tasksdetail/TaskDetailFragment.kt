package com.example.todoappmvp.tasksdetail

import android.content.Intent
import android.os.Bundle
import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todoappmvp.R
import com.example.todoappmvp.addedittask.AddEditTaskActivity
import com.example.todoappmvp.addedittask.AddEditTaskFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class TaskDetailFragment : Fragment(), TaskDetailContract.View {

    private lateinit var presenter: TaskDetailContract.Presenter
    private lateinit var detailTitle: TextView
    private lateinit var detailDescription: TextView
    private lateinit var detailCompleteStatus: CheckBox

    fun newInstance(taskId: String): TaskDetailFragment {
        val arguments = Bundle()
        arguments.putString(ARGUMENT_TASK_ID, taskId)
        val fragment = TaskDetailFragment()
        fragment.arguments = arguments
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.taskdetail_frag, container, false)
        setHasOptionsMenu(true)
        detailTitle = root.findViewById(R.id.task_detail_title) as TextView
        detailDescription = root.findViewById(R.id.task_detail_description) as TextView
        detailCompleteStatus = root.findViewById(R.id.task_detail_complete) as CheckBox
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab_edit_task)
        fab?.setOnClickListener { presenter.editTask() }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            detailTitle.text = ""
            detailDescription.text = getString(R.string.loading)
        }
    }

    override fun showMissingTask() {
        detailTitle.text = ""
        detailDescription.text = getString(R.string.no_data)
    }

    override fun hideTitle() {
        detailTitle.visibility = View.GONE
    }

    override fun showTitle(title: String) {
        detailTitle.visibility = View.VISIBLE
        detailTitle.text = title
    }

    override fun hideDescription() {
        detailDescription.visibility = View.GONE
    }

    override fun showDescription(description: String) {
        detailDescription.visibility = View.VISIBLE
        detailDescription.text = description
    }

    override fun showCompletionStatus(complete: Boolean) {
        detailCompleteStatus.isChecked = complete
        detailCompleteStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                presenter.completeTask()
            } else {
                presenter.activateTask()
            }
        }
    }

    override fun showEditTask(taskId: String) {
        val intent = Intent(context, AddEditTaskActivity::class.java)
        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId)
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }

    override fun showTaskDeleted() {
        activity?.finish()
    }

    override fun showTaskMarkedComplete() {
        view?.let { view ->
            Snackbar.make(
                view,
                getString(R.string.task_marked_complete),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun showTaskMarkedActive() {
        view?.let { view ->
            Snackbar.make(
                view,
                getString(R.string.task_marked_complete),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun isActive(): Boolean = isAdded

    override fun setPresenter(presenter: TaskDetailContract.Presenter) {
        this.presenter = checkNotNull(presenter)
    }

    private companion object {
        const val REQUEST_EDIT_TASK = 1
        const val ARGUMENT_TASK_ID = "TASK_ID"
    }
}
