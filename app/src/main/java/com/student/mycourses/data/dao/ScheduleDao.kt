package com.student.mycourses.data.dao

import androidx.room.*
import com.student.mycourses.data.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules ORDER BY startTime ASC")
    fun getAllSchedules(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedules WHERE dayOfWeek = :dayOfWeek ORDER BY startTime ASC")
    fun getSchedulesByDay(dayOfWeek: Int): Flow<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule): Long

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)
}
