package com.student.mycourses.data.dao

import androidx.room.*
import com.student.mycourses.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dueDate BETWEEN :startDate AND :endDate ORDER BY dueDate ASC")
    fun getTasksByDateRange(startDate: Long, endDate: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE courseId = :courseId ORDER BY dueDate ASC")
    fun getTasksByCourse(courseId: Long): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
