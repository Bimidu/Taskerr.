package com.example.labexam04.database.repositories

import com.example.labexam04.database.TaskDB
import com.example.labexam04.database.entities.Task

class TaskRepository (
    private val db:TaskDB
) {
    suspend fun insert(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun delete(task:Task) = db.getTaskDao().deleteTask(task)
    suspend fun update(task:Task) = db.getTaskDao().updateTask(task)
    fun getAllTaskItems():List<Task> = db.getTaskDao().getAllTaskItems()
}