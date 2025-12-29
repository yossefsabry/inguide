package com.inguide.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inguide.app.ui.auth.LoginScreen
import com.inguide.app.ui.auth.RegisterScreen
import com.inguide.app.ui.main.MainScreen
import com.inguide.app.ui.profile.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object Notifications : Screen("notifications")
    object Privacy : Screen("privacy")
    object Dashboard : Screen("dashboard")
    object DangerZone : Screen("danger_zone")
    object ForgotPassword : Screen("forgot_password")
    object AddSchedule : Screen("add_schedule")
    object ScheduleDetail : Screen("schedule_detail/{scheduleId}")
}

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }
    
    val startDestination = if (isAuthenticated) Screen.Main.route else Screen.Login.route
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    isAuthenticated = true
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    isAuthenticated = true
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ForgotPassword.route) {
            com.inguide.app.ui.auth.ForgotPasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = "${Screen.Main.route}?tab={tab}",
            arguments = listOf(
                androidx.navigation.navArgument("tab") {
                    type = androidx.navigation.NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val tab = backStackEntry.arguments?.getInt("tab") ?: 0
            MainScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
                onLogout = {
                    isAuthenticated = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                initialTab = tab
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
                onLogout = {
                    isAuthenticated = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }
        
        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }
        
        composable(Screen.Privacy.route) {
            PrivacyScreen(navController = navController)
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        
        composable(Screen.DangerZone.route) {
            DangerZoneScreen(navController = navController)
        }
        
        composable(Screen.AddSchedule.route) {
            com.inguide.app.ui.schedule.AddScheduleScreen(navController = navController)
        }
        
        composable(
            route = Screen.ScheduleDetail.route,
            arguments = listOf(
                androidx.navigation.navArgument("scheduleId") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val scheduleId = backStackEntry.arguments?.getString("scheduleId") ?: return@composable
            com.inguide.app.ui.schedule.ScheduleDetailScreen(
                scheduleId = scheduleId,
                navController = navController
            )
        }
    }
}
