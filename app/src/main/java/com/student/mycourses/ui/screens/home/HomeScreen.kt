package com.student.mycourses.ui.screens.home

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.student.mycourses.data.model.Course
import com.student.mycourses.ui.theme.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToCourses: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = OffWhite,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddTaskDialog() },
                containerColor = MintGreenDark,
                contentColor = White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Top bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = DarkText
                        )
                    }
                }
            }

            // Course progress banner card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(MintGreen, MintGreenLight)
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Take a look at your\ncourses & track your\nprogress",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = DarkText,
                                    lineHeight = 22.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = onNavigateToCourses,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = White,
                                        contentColor = DarkText
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Go to courses",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MintGreenDark.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MintGreenDark
                                )
                            }
                        }
                    }
                }
            }

            // Premium card
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(LightYellow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = LightYellowAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Go To Premium Now",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Support us & get unlimited access\nto the premium features",
                                style = MaterialTheme.typography.bodySmall,
                                color = SubtitleText,
                                lineHeight = 16.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = null,
                            tint = MediumGray
                        )
                    }
                }
            }

            // Tasks section header
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tasks",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "See All",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SubtitleText
                    )
                }
            }

            // Filter chips
            item {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf(
                        TaskFilter.YESTERDAY to "Yesterday",
                        TaskFilter.TODAY to "Today",
                        TaskFilter.TOMORROW to "Tomorrow",
                        TaskFilter.THIS_WEEK to "This Week"
                    )
                    items(filters) { (filter, label) ->
                        val isSelected = uiState.selectedFilter == filter
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.selectFilter(filter) },
                            label = {
                                Text(
                                    text = label,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ChipSelectedBg,
                                selectedLabelColor = DarkText,
                                containerColor = White,
                                labelColor = SubtitleText
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }

            // Tasks content
            if (uiState.tasks.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MintGreenLight)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MintGreen.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp),
                                    tint = MintGreenDark
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "You don't have any\ntasks!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = DarkText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Press + button to add\nnew tasks",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = SubtitleText
                            )
                        }
                    }
                }
            } else {
                items(uiState.tasks) { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { viewModel.toggleTaskCompletion(task) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MintGreenDark
                                )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium,
                                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (task.isCompleted) SubtitleText else DarkText
                                )
                                if (task.description.isNotBlank()) {
                                    Text(
                                        text = task.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SubtitleText
                                    )
                                }
                            }
                            IconButton(
                                onClick = { viewModel.deleteTask(task) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Delete",
                                    tint = MediumGray,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Task Dialog
    if (uiState.showAddTaskDialog) {
        AddTaskDialog(
            courses = uiState.courses,
            onDismiss = { viewModel.hideAddTaskDialog() },
            onConfirm = { title, description, courseId, dueDate ->
                viewModel.addTask(title, description, courseId, dueDate)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskDialog(
    courses: List<Course>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Long, Long) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCourseId by remember { mutableStateOf(0L) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var courseDropdownExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Add New Task",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                // Title field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MintGreenDark,
                        focusedLabelColor = MintGreenDark,
                        cursorColor = MintGreenDark
                    )
                )

                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MintGreenDark,
                        focusedLabelColor = MintGreenDark,
                        cursorColor = MintGreenDark
                    )
                )

                // Course picker
                ExposedDropdownMenuBox(
                    expanded = courseDropdownExpanded,
                    onExpandedChange = { courseDropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = courses.find { it.id == selectedCourseId }?.name ?: "Select Course",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseDropdownExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MintGreenDark,
                            focusedLabelColor = MintGreenDark
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = courseDropdownExpanded,
                        onDismissRequest = { courseDropdownExpanded = false }
                    ) {
                        courses.forEach { course ->
                            DropdownMenuItem(
                                text = { Text(course.name) },
                                onClick = {
                                    selectedCourseId = course.id
                                    courseDropdownExpanded = false
                                },
                                leadingIcon = {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .clip(CircleShape)
                                            .background(
                                                try {
                                                    Color(android.graphics.Color.parseColor(course.colorHex))
                                                } catch (e: Exception) {
                                                    MintGreen
                                                }
                                            )
                                    )
                                }
                            )
                        }
                    }
                }

                // Date picker button
                OutlinedButton(
                    onClick = {
                        val cal = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                selectedDate = LocalDate.of(year, month + 1, day)
                            },
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MediumGray),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarMonth,
                        contentDescription = null,
                        tint = MintGreenDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Due: ${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}",
                        color = DarkText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val zone = ZoneId.systemDefault()
                        val epochMillis = selectedDate.atStartOfDay(zone).toInstant().toEpochMilli()
                        onConfirm(title, description, selectedCourseId, epochMillis)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MintGreenDark,
                    contentColor = White
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = title.isNotBlank()
            ) {
                Text("Add Task", fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = SubtitleText)
            }
        }
    )
}
