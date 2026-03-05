package com.student.mycourses

import android.app.Application
import com.student.mycourses.data.AppDatabase
import com.student.mycourses.data.repository.AppRepository

class MyCoursesApp : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        AppRepository(
            database.courseDao(),
            database.taskDao(),
            database.scheduleDao()
        )
    }
}
