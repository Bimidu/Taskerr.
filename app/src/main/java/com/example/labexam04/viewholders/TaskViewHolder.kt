package com.example.labexam04.viewholders

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.R

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var taskStatusCheckBox: CheckBox = itemView.findViewById(R.id.task_status)
    var taskNameTextView: TextView = itemView.findViewById(R.id.task_name)
    var taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_description)
    var taskDateTextView: TextView = itemView.findViewById(R.id.task_date)
    var taskTimeTextView: TextView = itemView.findViewById(R.id.task_time)
    var updateButton: Button = itemView.findViewById(R.id.update)
    var deleteButton: Button = itemView.findViewById(R.id.delete)

    init {
        taskStatusCheckBox = itemView.findViewById(R.id.task_status)
        taskNameTextView = itemView.findViewById(R.id.task_name)
        taskDescriptionTextView = itemView.findViewById(R.id.task_description)
        taskDateTextView = itemView.findViewById(R.id.task_date)
        taskTimeTextView = itemView.findViewById(R.id.task_time)
        updateButton = itemView.findViewById(R.id.update)
        deleteButton = itemView.findViewById(R.id.delete)
    }
}

