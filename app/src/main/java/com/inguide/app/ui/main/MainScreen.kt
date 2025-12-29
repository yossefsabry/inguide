package com.inguide.app.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.inguide.app.ui.chat.ChatScreen
import com.inguide.app.ui.home.HomeScreen
import com.inguide.app.ui.map.MapScreen
import com.inguide.app.ui.schedule.ScheduleScreen

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Map : BottomNavItem("map", "Map", Icons.Filled.Map, Icons.Outlined.Map)
    object Schedule : BottomNavItem("schedule", "Schedule", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth)
    object Chat : BottomNavItem("chat", "Chat", Icons.Filled.Chat, Icons.Outlined.Chat)
}

@Composable
fun MainScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onLogout: () -> Unit,
    initialTab: Int = 0
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Schedule,
        BottomNavItem.Chat
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
                onLogout = onLogout,
                onNavigateToTab = { index -> selectedTab = index }
            )
            1 -> MapScreen(modifier = Modifier.padding(paddingValues))
            2 -> ScheduleScreen(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
            3 -> ChatScreen(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
