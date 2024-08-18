package com.corevm.todolistapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.corevm.todolistapp.R
import com.corevm.todolistapp.data.Task
import com.corevm.todolistapp.viewmodel.TaskViewModel

class TaskAdapter(private val taskViewModel: TaskViewModel, private val deleteClick:(Task) -> Unit): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    
    private var tasks = emptyList<Task>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    fun submList(taskList: List<Task>) {
        tasks = taskList
        notifyDataSetChanged()
    }
    
    inner class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(item: Task) {

            checkBox.text = item.title
            checkBox.isChecked = item.isCompleted

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                taskViewModel.update(item.copy(isCompleted = isChecked))
            }

            btnDelete.setOnClickListener {
                deleteClick.invoke(item)
            }
        }
    }

}