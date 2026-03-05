package com.student.mycourses.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val courseId: Long = 0,
    val dueDate: Long = 0L, // epoch millis
    val isCompleted: Boolean = false
)
