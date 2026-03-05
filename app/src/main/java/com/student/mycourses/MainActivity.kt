package com.student.mycourses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.student.mycourses.ui.navigation.AppNavigation
import com.student.mycourses.ui.theme.MyCoursesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as MyCoursesApp

        setContent {
            MyCoursesTheme {
                AppNavigation(repository = app.repository)
            }
        }
    }
}
