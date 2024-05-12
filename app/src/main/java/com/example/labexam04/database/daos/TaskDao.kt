package com.example.labexam04.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.labexam04.database.entities.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(todo: Task)
    @Delete
    suspend fun deleteTask(todo: Task)
    @Query("SELECT * FROM Task")
    fun getAllTaskItems():List<Task>
}