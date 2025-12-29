package com.inguide.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.inguide.app.ui.theme.DesignSystem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy") },
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
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            item {
                PrivacyCard(
                    title = "Data Collection",
                    content = "InGuide collects location data to provide indoor navigation and positioning services. This data is processed locally on your device and is not transmitted to external servers."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                PrivacyCard(
                    title = "Location Services",
                    content = "We use GPS and magnetic field sensors for indoor positioning. Location data is used only for navigation purposes and is stored locally on your device."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                PrivacyCard(
                    title = "Camera Access",
                    content = "Camera access is used for AR navigation features. The camera feed is not recorded or saved. Images are processed in real-time for positioning only."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                PrivacyCard(
                    title = "Data Storage",
                    content = "Your schedule, chat history, and preferences are stored locally on your device using encrypted storage. You can delete this data at any time from the Danger Zone."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            item {
                PrivacyCard(
                    title = "Third-Party Services",
                    content = "We use Google Maps for map display. Please refer to Google's privacy policy for information about their data collection practices."
                )
            }
        }
    }
}

@Composable
fun PrivacyCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = DesignSystem.Shapes.CardShape
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
