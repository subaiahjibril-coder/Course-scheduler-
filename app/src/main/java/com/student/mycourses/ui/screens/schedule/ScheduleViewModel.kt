package com.student.mycourses.ui.screens.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.student.mycourses.data.model.Schedule
import com.student.mycourses.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class DayInfo(
    val dayAbbrev: String,
    val dayNumber: Int,
    val date: LocalDate,
    val isSelected: Boolean = false
)

data class ScheduleUiState(
    val weekDays: List<DayInfo> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val schedules: List<Schedule> = emptyList()
)

class ScheduleViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        val today = LocalDate.now()
        loadWeek(today)
        selectDate(today)
    }

    private fun loadWeek(referenceDate: LocalDate) {
        val monday = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val days = (0..6).map { offset ->
            val date = monday.plusDays(offset.toLong())
            val abbrevs = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
            DayInfo(
                dayAbbrev = abbrevs[offset],
                dayNumber = date.dayOfMonth,
                date = date,
                isSelected = date == referenceDate
            )
        }
        _uiState.update { it.copy(weekDays = days) }
    }

    fun selectDate(date: LocalDate) {
        _uiState.update { state ->
            state.copy(
                selectedDate = date,
                weekDays = state.weekDays.map { it.copy(isSelected = it.date == date) }
            )
        }
        // dayOfWeek: 1=Monday .. 7=Sunday
        val dayOfWeek = date.dayOfWeek.value
        viewModelScope.launch {
            repository.getSchedulesByDay(dayOfWeek).collect { schedules ->
                _uiState.update { it.copy(schedules = schedules) }
            }
        }
    }

    class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScheduleViewModel(repository) as T
        }
    }
}
