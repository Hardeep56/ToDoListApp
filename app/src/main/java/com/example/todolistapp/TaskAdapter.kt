package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.TaskItemBinding

class TaskAdapter(private val dbHelper: DatabaseHelper) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<Task>()

    class TaskViewHolder(binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val taskName: TextView = binding.taskName
        val completedCheckbox: CheckBox = binding.completedCheckbox
        val deleteButton: View = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.completedCheckbox.isChecked = task.isCompleted

        holder.completedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            dbHelper.updateTask(task)
        }

        holder.deleteButton.setOnClickListener {
            dbHelper.deleteTask(task.id)
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = tasks.size

    fun setTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }
}