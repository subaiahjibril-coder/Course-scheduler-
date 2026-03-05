package com.student.mycourses.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.student.mycourses.data.dao.CourseDao
import com.student.mycourses.data.dao.ScheduleDao
import com.student.mycourses.data.dao.TaskDao
import com.student.mycourses.data.model.Course
import com.student.mycourses.data.model.Schedule
import com.student.mycourses.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Course::class, Task::class, Schedule::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun taskDao(): TaskDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mycourses_database"
                )
                    .addCallback(SeedDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedDatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database)
                }
            }
        }

        suspend fun populateDatabase(database: AppDatabase) {
            val courseDao = database.courseDao()
            val scheduleDao = database.scheduleDao()

            // Sample courses
            val mathId = courseDao.insertCourse(
                Course(name = "Basic Mathematics", colorHex = "#C8E6C0")
            )
            val englishId = courseDao.insertCourse(
                Course(name = "English Grammar", colorHex = "#FFF9C4")
            )
            val scienceId = courseDao.insertCourse(
                Course(name = "Science", colorHex = "#E1F5FE")
            )
            val historyId = courseDao.insertCourse(
                Course(name = "World History", colorHex = "#FCE4EC")
            )

            // Sample schedules (Monday = 1)
            // Monday schedule
            scheduleDao.insertSchedule(
                Schedule(courseId = mathId, courseName = "Basic Mathematics", colorHex = "#C8E6C0", dayOfWeek = 1, startTime = "08:00am", endTime = "09:45am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = englishId, courseName = "English Grammar", colorHex = "#FFF9C4", dayOfWeek = 1, startTime = "10:00am", endTime = "11:30am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = scienceId, courseName = "Science", colorHex = "#E1F5FE", dayOfWeek = 1, startTime = "12:00pm", endTime = "12:45pm")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = historyId, courseName = "World History", colorHex = "#FCE4EC", dayOfWeek = 1, startTime = "01:00pm", endTime = "01:45pm")
            )

            // Tuesday schedule
            scheduleDao.insertSchedule(
                Schedule(courseId = englishId, courseName = "English Grammar", colorHex = "#FFF9C4", dayOfWeek = 2, startTime = "09:00am", endTime = "10:30am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = mathId, courseName = "Basic Mathematics", colorHex = "#C8E6C0", dayOfWeek = 2, startTime = "11:00am", endTime = "12:00pm")
            )

            // Wednesday schedule
            scheduleDao.insertSchedule(
                Schedule(courseId = scienceId, courseName = "Science", colorHex = "#E1F5FE", dayOfWeek = 3, startTime = "08:00am", endTime = "09:30am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = historyId, courseName = "World History", colorHex = "#FCE4EC", dayOfWeek = 3, startTime = "10:00am", endTime = "11:00am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = mathId, courseName = "Basic Mathematics", colorHex = "#C8E6C0", dayOfWeek = 3, startTime = "01:00pm", endTime = "02:30pm")
            )

            // Thursday schedule
            scheduleDao.insertSchedule(
                Schedule(courseId = englishId, courseName = "English Grammar", colorHex = "#FFF9C4", dayOfWeek = 4, startTime = "08:00am", endTime = "09:45am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = scienceId, courseName = "Science", colorHex = "#E1F5FE", dayOfWeek = 4, startTime = "10:00am", endTime = "11:30am")
            )

            // Friday schedule
            scheduleDao.insertSchedule(
                Schedule(courseId = mathId, courseName = "Basic Mathematics", colorHex = "#C8E6C0", dayOfWeek = 5, startTime = "09:00am", endTime = "10:30am")
            )
            scheduleDao.insertSchedule(
                Schedule(courseId = historyId, courseName = "World History", colorHex = "#FCE4EC", dayOfWeek = 5, startTime = "11:00am", endTime = "12:00pm")
            )
        }
    }
}
