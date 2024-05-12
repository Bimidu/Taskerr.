package com.example.labexam04.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.labexam04.database.daos.TaskDao
import com.example.labexam04.database.entities.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDB: RoomDatabase(){
    abstract fun getTaskDao(): TaskDao
    companion object{
        @Volatile
        private var INSTANCE: TaskDB? = null
        fun getInstance(context: Context):TaskDB{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDB::class.java,
                    "task_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}