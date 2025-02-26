package com.example.todolistapp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity(), Parcelable {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        taskAdapter = TaskAdapter(dbHelper)

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = taskAdapter
        loadTasks()
    }

    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            val taskName = binding.taskInput.text.toString().trim()
            if (taskName.isNotEmpty()) {
                dbHelper.addTask(taskName)
                binding.taskInput.setText("")
                loadTasks()
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTasks() {
        taskAdapter.setTasks(dbHelper.getAllTasks())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}