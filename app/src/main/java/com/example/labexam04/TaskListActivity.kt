package com.example.labexam04

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam04.adapters.TaskAdapter
import com.example.labexam04.database.TaskDB
import com.example.labexam04.database.entities.Task
import com.example.labexam04.database.repositories.TaskRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskListActivity : AppCompatActivity() {
    private lateinit var adapter:TaskAdapter
    private lateinit var viewModel:TaskListActivityData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val repository = TaskRepository(TaskDB.getInstance(this))
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        viewModel = ViewModelProvider(this)[TaskListActivityData::class.java]

        viewModel.data.observe(this){
            adapter = TaskAdapter(it,repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllTaskItems()
            runOnUiThread {
                viewModel.setData(data)
            }
        }

        val plusButton = findViewById<Button>(R.id.plusButton)

        plusButton.setOnClickListener {
            displayDialog(repository)
        }


    }



    fun displayDialog(repository: TaskRepository) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.task_add_form_layout, null)

        // Get references to EditText fields from the dialog layout
        val taskNameEditText = dialogView.findViewById<EditText>(R.id.editTextTaskName)
        val taskDescriptionEditText = dialogView.findViewById<EditText>(R.id.editTextTaskDescription)
        val editTextDate = dialogView.findViewById<EditText>(R.id.editTextDate)
        val editTextTime = dialogView.findViewById<EditText>(R.id.editTextTime)

        // Set the alert dialog title and message
        builder.setTitle("Enter New Todo item:")
        builder.setMessage("Enter the todo item below:")

        // Set the custom layout as the dialog view
        builder.setView(dialogView)

        // Set onClickListener for Date EditText
        editTextDate.setOnClickListener {
            showDatePickerDialog(this, editTextDate)
        }

        // Set onClickListener for Time EditText
        editTextTime.setOnClickListener {
            showTimePickerDialog(this, editTextTime)
        }

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, which ->
            // Get the input text and display a Toast message
            val item = taskNameEditText.text.toString()
            val description = taskDescriptionEditText.text.toString()
            val date = editTextDate.text.toString()
            val time = editTextTime.text.toString()


            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Task(item, description, date, time))
                val data = repository.getAllTaskItems()
                runOnUiThread {
                    viewModel.setData(data)
                }
            }
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        // Create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun showDatePickerDialog(activity: AppCompatActivity, editTextDate: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(activity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                editTextDate.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            }, year, month, day)
        datePickerDialog.show()
    }

    fun showTimePickerDialog(activity: AppCompatActivity, editTextTime: EditText) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(activity,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                editTextTime.setText("$hourOfDay:$minute")
            }, hour, minute, false)
        timePickerDialog.show()
    }




}