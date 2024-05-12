package com.example.labexam04.adapters

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.R
import com.example.labexam04.TaskListActivityData
import com.example.labexam04.database.entities.Task
import com.example.labexam04.database.repositories.TaskRepository
import com.example.labexam04.viewholders.TaskViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskAdapter(items:List<Task>, repository: TaskRepository,
                  viewModel:TaskListActivityData): RecyclerView.Adapter<TaskViewHolder>() {

    var context: Context? = null
    val items = items
    val repository = repository
    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        context = parent.context
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.taskNameTextView.text = items.get(position).item

        holder.taskDescriptionTextView.text = items.get(position).itemDescription

        holder.taskDateTextView.text = items.get(position).itemDate

        holder.taskTimeTextView.text = items.get(position).itemTime

        holder.deleteButton.setOnClickListener{
            val isChecked = holder.taskStatusCheckBox.isChecked
            if(isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(items.get(position))
                    val data = repository.getAllTaskItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
                Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context,"Select the item to delete",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }




}

