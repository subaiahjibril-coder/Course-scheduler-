package com.student.mycourses.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.student.mycourses.ui.theme.*

val themeColors = listOf(
    "#C8E6C0" to "Mint Green",
    "#FFF9C4" to "Light Yellow",
    "#FCE4EC" to "Soft Pink",
    "#E1F5FE" to "Pastel Blue",
    "#D1C4E9" to "Lavender",
    "#FFE0B2" to "Peach",
    "#B2DFDB" to "Teal",
    "#F8BBD0" to "Rose",
    "#90CAF9" to "Blue",
    "#CE93D8" to "Purple",
    "#FFD54F" to "Amber",
    "#A5D6A7" to "Green"
)

val languages = listOf(
    "English" to "English",
    "French" to "Français",
    "Arabic" to "العربية",
    "Spanish" to "Español",
    "German" to "Deutsch",
    "Turkish" to "Türkçe"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
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
            // Title
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // App branding header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Info",
                                tint = MediumGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(color = OffWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AutoStories,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MintGreenDark
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "My Courses",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = uiState.appVersion,
                            style = MaterialTheme.typography.bodySmall,
                            color = SubtitleText,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ChipSelectedBg,
                                contentColor = DarkText
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            Text(
                                text = "Go To Premium with My Courses",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Settings section header
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Settings items
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column {
                        SettingsItem(
                            icon = Icons.Rounded.Palette,
                            iconBgColor = SoftPink,
                            iconTint = SoftPinkAccent,
                            title = "Theme Color",
                            subtitle = themeColors.find { it.first == uiState.selectedThemeColor }?.second,
                            onClick = { viewModel.showThemeDialog() }
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = LightGray,
                            thickness = 0.5.dp
                        )
                        SettingsItem(
                            icon = Icons.Rounded.Language,
                            iconBgColor = PastelBlue,
                            iconTint = PastelBlueAccent,
                            title = "Change Language",
                            subtitle = uiState.selectedLanguage,
                            onClick = { viewModel.showLanguageDialog() }
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = LightGray,
                            thickness = 0.5.dp
                        )
                        SettingsItem(
                            icon = Icons.Rounded.CloudUpload,
                            iconBgColor = MintGreenLight,
                            iconTint = MintGreenDark,
                            title = "Create Backup File"
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = LightGray,
                            thickness = 0.5.dp
                        )
                        SettingsItem(
                            icon = Icons.Rounded.Restore,
                            iconBgColor = LightYellow,
                            iconTint = LightYellowAccent,
                            title = "Restore Data"
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = LightGray,
                            thickness = 0.5.dp
                        )
                        SettingsItem(
                            icon = Icons.Rounded.NotificationsOff,
                            iconBgColor = SoftPink,
                            iconTint = SoftPinkAccent,
                            title = "Cancel All Scheduled\nNotifications"
                        )
                    }
                }
            }
        }
    }

    // Theme Color Dialog
    if (uiState.showThemeDialog) {
        ThemeColorDialog(
            selectedColor = uiState.selectedThemeColor,
            onDismiss = { viewModel.hideThemeDialog() },
            onSelect = { viewModel.setThemeColor(it) }
        )
    }

    // Language Dialog
    if (uiState.showLanguageDialog) {
        LanguageDialog(
            selectedLanguage = uiState.selectedLanguage,
            onDismiss = { viewModel.hideLanguageDialog() },
            onSelect = { viewModel.setLanguage(it) }
        )
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = SubtitleText,
                    fontSize = 12.sp
                )
            }
        }
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = MediumGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun ThemeColorDialog(
    selectedColor: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Theme Color",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Choose your preferred theme color",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SubtitleText
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Color grid (4 columns)
                val rows = themeColors.chunked(4)
                rows.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { (hex, name) ->
                            val color = try {
                                Color(android.graphics.Color.parseColor(hex))
                            } catch (e: Exception) {
                                MintGreen
                            }
                            val isSelected = selectedColor == hex

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable { onSelect(hex) }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .then(
                                            if (isSelected) Modifier.border(3.dp, DarkText, CircleShape)
                                            else Modifier
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "Selected",
                                            tint = DarkText,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SubtitleText,
                                    fontSize = 9.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", color = MintGreenDark, fontWeight = FontWeight.SemiBold)
            }
        }
    )
}

@Composable
private fun LanguageDialog(
    selectedLanguage: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Select Language",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                languages.forEach { (key, nativeName) ->
                    val isSelected = selectedLanguage == key
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) MintGreenLight else Color.Transparent
                            )
                            .clickable { onSelect(key) }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = DarkText
                            )
                            Text(
                                text = nativeName,
                                style = MaterialTheme.typography.bodySmall,
                                color = SubtitleText
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Selected",
                                tint = MintGreenDark,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done", color = MintGreenDark, fontWeight = FontWeight.SemiBold)
            }
        }
    )
}
