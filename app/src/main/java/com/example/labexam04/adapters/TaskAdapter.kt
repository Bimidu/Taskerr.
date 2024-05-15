package com.example.labexam04.adapters

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.util.Calendar

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
        val currentItem = items[position]

        holder.taskNameTextView.text = items.get(position).item

        holder.taskDescriptionTextView.text = items.get(position).itemDescription

        holder.taskDateTextView.text = items.get(position).itemDate

        holder.taskTimeTextView.text = items.get(position).itemTime

        holder.updateButton.setOnClickListener {
            showEditDialog(currentItem)
        }

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


    private fun showEditDialog(task: Task) {
        val builder = AlertDialog.Builder(context!!)

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.task_add_form_layout, null)

        val editTextTaskName = dialogView.findViewById<EditText>(R.id.editTextTaskName)
        val editTextTaskDescription = dialogView.findViewById<EditText>(R.id.editTextTaskDescription)
        val editTextDate = dialogView.findViewById<EditText>(R.id.editTextDate)
        val editTextTime = dialogView.findViewById<EditText>(R.id.editTextTime)

        editTextTaskName.setText(task.item)
        editTextTaskDescription.setText(task.itemDescription)
        editTextDate.setText(task.itemDate)
        editTextTime.setText(task.itemTime)

        editTextDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context!!,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    editTextDate.setText(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        editTextTime.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(context!!,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    editTextTime.setText("$hourOfDay:$minute")
                }, hour, minute, false)
            timePickerDialog.show()
        }

        builder.setView(dialogView)
            .setTitle("Edit Task")
            .setPositiveButton("Save") { dialog, _ ->
                val updatedTask = Task(
                    editTextTaskName.text.toString(),
                    editTextTaskDescription.text.toString(),
                    editTextDate.text.toString(),
                    editTextTime.text.toString()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    repository.update(updatedTask)
                    val data = repository.getAllTaskItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

}

