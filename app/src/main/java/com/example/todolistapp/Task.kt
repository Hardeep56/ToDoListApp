package com.example.todolistapp

data class Task(
    var id: Int = 0,
    var name: String = "",
    var isCompleted: Boolean = false
)