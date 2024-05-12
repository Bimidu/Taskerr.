package com.example.labexam04

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labexam04.database.entities.Task

class TaskListActivityData: ViewModel() {
    private val _data = MutableLiveData<List<Task>>()
    val data: LiveData<List<Task>> = _data
    fun setData(data:List<Task>){
        _data.value = data
    }
}