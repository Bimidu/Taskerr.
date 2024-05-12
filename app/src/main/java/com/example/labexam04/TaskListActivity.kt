package com.example.labexam04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
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

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.navigation_tasks
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.navigation_tasks -> {
                    val intent = Intent(this, TaskListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_notifications -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }



    fun displayDialog(repository: TaskRepository){
        val builder = AlertDialog.Builder(this)

        // Set the alert dialog title and message
        builder.setTitle("Enter New Todo item:")
        builder.setMessage("Enter the todo item below:")

        // Create an EditText input field
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, which ->

            // Get the input text and display a Toast message
            val item = input.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Task(item))
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



}