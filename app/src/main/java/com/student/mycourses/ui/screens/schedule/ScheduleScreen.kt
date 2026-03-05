package com.student.mycourses.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.student.mycourses.data.model.Schedule
import com.student.mycourses.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = OffWhite
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
                        text = "Schedule",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = DarkText
                        )
                    }
                }
            }

            // "This week" + "See all"
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "This week",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SubtitleText
                    )
                }
            }

            // Day selector row
            item {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.weekDays) { day ->
                        DayChip(
                            day = day,
                            onClick = { viewModel.selectDate(day.date) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Timeline
            if (uiState.schedules.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No classes scheduled\nfor this day",
                            style = MaterialTheme.typography.bodyLarge,
                            color = SubtitleText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                items(uiState.schedules) { schedule ->
                    ScheduleTimelineItem(schedule = schedule)
                }
            }
        }
    }
}

@Composable
private fun DayChip(
    day: DayInfo,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(
                if (day.isSelected) ChipSelectedBg else White,
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayAbbrev.take(3),
            style = MaterialTheme.typography.labelMedium,
            color = if (day.isSelected) DarkText else SubtitleText,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    if (day.isSelected) DarkText else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${day.dayNumber}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (day.isSelected) White else DarkText,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun ScheduleTimelineItem(
    schedule: Schedule
) {
    val bgColor = try {
        Color(android.graphics.Color.parseColor(schedule.colorHex))
    } catch (e: Exception) {
        MintGreen
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Time label
        Column(
            modifier = Modifier.width(70.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = schedule.startTime,
                style = MaterialTheme.typography.bodySmall,
                color = SubtitleText,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Vertical line
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(70.dp)
                .background(LightGray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Course card
        Card(
            modifier = Modifier
                .weight(1f)
                .height(64.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = bgColor.copy(alpha = 0.6f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = schedule.courseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${schedule.startTime} - ${schedule.endTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = DarkText.copy(alpha = 0.6f),
                    fontSize = 11.sp
                )
            }
        }
    }
}
