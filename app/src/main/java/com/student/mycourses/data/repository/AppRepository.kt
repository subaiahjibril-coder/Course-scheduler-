package com.student.mycourses.data.repository

import com.student.mycourses.data.dao.CourseDao
import com.student.mycourses.data.dao.ScheduleDao
import com.student.mycourses.data.dao.TaskDao
import com.student.mycourses.data.model.Course
import com.student.mycourses.data.model.Schedule
import com.student.mycourses.data.model.Task
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val courseDao: CourseDao,
    private val taskDao: TaskDao,
    private val scheduleDao: ScheduleDao
) {
    // Courses
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun getCourseById(id: Long): Course? = courseDao.getCourseById(id)
    suspend fun insertCourse(course: Course): Long = courseDao.insertCourse(course)
    suspend fun deleteCourse(course: Course) = courseDao.deleteCourse(course)

    // Tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    fun getTasksByDateRange(startDate: Long, endDate: Long): Flow<List<Task>> =
        taskDao.getTasksByDateRange(startDate, endDate)

    fun getTasksByCourse(courseId: Long): Flow<List<Task>> =
        taskDao.getTasksByCourse(courseId)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    // Schedules
    val allSchedules: Flow<List<Schedule>> = scheduleDao.getAllSchedules()

    fun getSchedulesByDay(dayOfWeek: Int): Flow<List<Schedule>> =
        scheduleDao.getSchedulesByDay(dayOfWeek)

    suspend fun insertSchedule(schedule: Schedule): Long = scheduleDao.insertSchedule(schedule)
    suspend fun deleteSchedule(schedule: Schedule) = scheduleDao.deleteSchedule(schedule)
}
