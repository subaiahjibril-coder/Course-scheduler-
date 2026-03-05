package com.student.mycourses.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.student.mycourses.data.repository.AppRepository
import com.student.mycourses.ui.screens.courses.CoursesScreen
import com.student.mycourses.ui.screens.courses.CoursesViewModel
import com.student.mycourses.ui.screens.home.HomeScreen
import com.student.mycourses.ui.screens.home.HomeViewModel
import com.student.mycourses.ui.screens.schedule.ScheduleScreen
import com.student.mycourses.ui.screens.schedule.ScheduleViewModel
import com.student.mycourses.ui.screens.settings.SettingsScreen
import com.student.mycourses.ui.screens.settings.SettingsViewModel
import com.student.mycourses.ui.theme.*

sealed class Screen(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Courses : Screen("courses", "Courses", Icons.Filled.MenuBook, Icons.Outlined.MenuBook)
    data object Schedule : Screen("schedule", "Schedule", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
}

@Composable
fun AppNavigation(
    repository: AppRepository
) {
    val navController = rememberNavController()
    val screens = listOf(Screen.Home, Screen.Courses, Screen.Schedule, Screen.Settings)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = OffWhite,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                containerColor = White,
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                contentDescription = screen.label,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DarkText,
                            selectedTextColor = DarkText,
                            unselectedIconColor = MediumGray,
                            unselectedTextColor = MediumGray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val homeViewModel: HomeViewModel = viewModel(
                    factory = HomeViewModel.Factory(repository)
                )
                HomeScreen(
                    viewModel = homeViewModel,
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    },
                    onNavigateToCourses = {
                        navController.navigate(Screen.Courses.route)
                    }
                )
            }
            composable(Screen.Courses.route) {
                val coursesViewModel: CoursesViewModel = viewModel(
                    factory = CoursesViewModel.Factory(repository)
                )
                CoursesScreen(viewModel = coursesViewModel)
            }
            composable(Screen.Schedule.route) {
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(repository)
                )
                ScheduleScreen(viewModel = scheduleViewModel)
            }
            composable(Screen.Settings.route) {
                val settingsViewModel: SettingsViewModel = viewModel()
                SettingsScreen(viewModel = settingsViewModel)
            }
        }
    }
}
