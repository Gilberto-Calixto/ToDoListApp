package com.corevm.todolistapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corevm.todolistapp.R
import com.corevm.todolistapp.data.Task
import com.corevm.todolistapp.viewmodel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcv: RecyclerView = findViewById(R.id.recyclerView)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        val adapter = TaskAdapter(viewModel){ task -> viewModel.delete(task) }
        rcv.adapter =  adapter
        rcv.layoutManager = LinearLayoutManager(this)

        viewModel.allTasks.observe(this, Observer {
            it?.let { adapter.submList(it) }
        })

        fab.setOnClickListener {
            showAddTaskDialog()
        }

    }

    private fun showAddTaskDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextTask)

        MaterialAlertDialogBuilder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") {_, bt ->
                val taskTitle = editText.text.toString()
                if (taskTitle.isNotEmpty()) { viewModel.insert(Task(title = taskTitle))}
            }
            .setNegativeButton("cancel", null)
            .show()
    }
}