package com.student.mycourses.ui.screens.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.student.mycourses.data.model.Course
import com.student.mycourses.data.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CoursesUiState(
    val courses: List<Course> = emptyList(),
    val showAddDialog: Boolean = false
)

class CoursesViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CoursesUiState())
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allCourses.collect { courses ->
                _uiState.update { it.copy(courses = courses) }
            }
        }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addCourse(name: String, colorHex: String) {
        viewModelScope.launch {
            repository.insertCourse(Course(name = name, colorHex = colorHex))
            _uiState.update { it.copy(showAddDialog = false) }
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.deleteCourse(course)
        }
    }

    class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CoursesViewModel(repository) as T
        }
    }
}
