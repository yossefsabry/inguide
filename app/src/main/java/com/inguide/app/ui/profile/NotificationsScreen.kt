package com.inguide.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.inguide.app.ui.theme.DesignSystem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    var scheduleNotifications by remember { mutableStateOf(true) }
    var chatNotifications by remember { mutableStateOf(true) }
    var locationNotifications by remember { mutableStateOf(false) }
    var beforeClassMinutes by remember { mutableStateOf(10) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ChevronLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(DesignSystem.Dimensions.PaddingMedium)
        ) {
            item {
                Text(
                    text = "Notification Preferences",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            // Schedule Notifications
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Schedule Notifications",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Get notified before classes and labs",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = scheduleNotifications,
                                onCheckedChange = { scheduleNotifications = it }
                            )
                        }
                        
                        if (scheduleNotifications) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Notify me $beforeClassMinutes minutes before class",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            
                            Slider(
                                value = beforeClassMinutes.toFloat(),
                                onValueChange = { beforeClassMinutes = it.toInt() },
                                valueRange = 5f..30f,
                                steps = 4
                            )
                            
                            Text(
                                text = "$beforeClassMinutes minutes",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Chat Notifications
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Chat Notifications",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Get notified of new messages",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = chatNotifications,
                            onCheckedChange = { chatNotifications = it }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Location Notifications
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Location Notifications",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Get notified when arriving at locations",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = locationNotifications,
                            onCheckedChange = { locationNotifications = it }
                        )
                    }
                }
            }
        }
    }
}
