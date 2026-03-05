package com.student.mycourses.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseId: Long,
    val courseName: String,
    val colorHex: String,
    val dayOfWeek: Int, // 1=Monday, 7=Sunday
    val startTime: String, // "09:00"
    val endTime: String    // "09:45"
)
