package com.example.labexam04.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.labexam04.database.entities.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)
    @Update
    suspend fun updateTask(task: Task)
    @Query("SELECT * FROM Task")
    fun getAllTaskItems():List<Task>
}