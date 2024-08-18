package com.corevm.todolistapp.repository

import androidx.lifecycle.LiveData
import com.corevm.todolistapp.data.Task
import com.corevm.todolistapp.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    //Variáveis a ser observadas -> (dados buscados/recebidos)
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()


    //Operações a serem realizadas -> (buscando dados)
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}