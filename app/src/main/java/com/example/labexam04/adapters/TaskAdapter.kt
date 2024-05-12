package com.example.labexam04.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.R
import com.example.labexam04.viewholders.TaskViewHolder

class TaskAdapter(private val taskList: List<String>) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.textView.text = taskList[position]
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}
