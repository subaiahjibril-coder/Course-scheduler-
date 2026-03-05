package com.student.mycourses.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.student.mycourses.data.model.Course
import com.student.mycourses.data.model.Task
import com.student.mycourses.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

enum class TaskFilter { YESTERDAY, TODAY, TOMORROW, THIS_WEEK }

data class HomeUiState(
    val courses: List<Course> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val selectedFilter: TaskFilter = TaskFilter.TODAY,
    val showAddTaskDialog: Boolean = false
)

class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allCourses.collect { courses ->
                _uiState.update { it.copy(courses = courses) }
            }
        }
        loadTasks(TaskFilter.TODAY)
    }

    fun selectFilter(filter: TaskFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        loadTasks(filter)
    }

    fun showAddTaskDialog() {
        _uiState.update { it.copy(showAddTaskDialog = true) }
    }

    fun hideAddTaskDialog() {
        _uiState.update { it.copy(showAddTaskDialog = false) }
    }

    fun addTask(title: String, description: String, courseId: Long, dueDate: Long) {
        viewModelScope.launch {
            repository.insertTask(
                Task(
                    title = title,
                    description = description,
                    courseId = courseId,
                    dueDate = dueDate
                )
            )
            _uiState.update { it.copy(showAddTaskDialog = false) }
            // Reload tasks for current filter
            loadTasks(_uiState.value.selectedFilter)
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    private fun loadTasks(filter: TaskFilter) {
        viewModelScope.launch {
            val today = LocalDate.now()
            val zone = ZoneId.systemDefault()
            val (start, end) = when (filter) {
                TaskFilter.YESTERDAY -> {
                    val yesterday = today.minusDays(1)
                    Pair(
                        yesterday.atStartOfDay(zone).toInstant().toEpochMilli(),
                        yesterday.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
                    )
                }
                TaskFilter.TODAY -> Pair(
                    today.atStartOfDay(zone).toInstant().toEpochMilli(),
                    today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
                )
                TaskFilter.TOMORROW -> {
                    val tomorrow = today.plusDays(1)
                    Pair(
                        tomorrow.atStartOfDay(zone).toInstant().toEpochMilli(),
                        tomorrow.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
                    )
                }
                TaskFilter.THIS_WEEK -> Pair(
                    today.atStartOfDay(zone).toInstant().toEpochMilli(),
                    today.plusDays(7).atStartOfDay(zone).toInstant().toEpochMilli() - 1
                )
            }
            repository.getTasksByDateRange(start, end).collect { tasks ->
                _uiState.update { it.copy(tasks = tasks) }
            }
        }
    }

    class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}
