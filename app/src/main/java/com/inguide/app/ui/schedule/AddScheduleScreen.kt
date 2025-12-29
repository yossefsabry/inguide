package com.inguide.app.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.inguide.app.data.ScheduleDataStore
import com.inguide.app.data.model.ScheduleItem
import com.inguide.app.ui.theme.Primary
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scheduleDataStore = remember { ScheduleDataStore.getInstance(context) }
    
    var title by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("Monday") }
    var type by remember { mutableStateOf("Lecture") }
    var selectedColor by remember { mutableStateOf("#007AFF") }
    
    val colors = listOf(
        "#007AFF", // Blue
        "#FF9500", // Orange
        "#34C759", // Green
        "#AF52DE", // Purple
        "#FF2D55", // Pink
        "#5856D6"  // Indigo
    )
    
    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    var showDayDropdown by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Class") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Class Name (e.g. CS 101)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Room Input
            OutlinedTextField(
                value = room,
                onValueChange = { room = it },
                label = { Text("Room / Location") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            // Type Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChip(
                    selected = type == "Lecture",
                    onClick = { type = "Lecture" },
                    label = { Text("Lecture") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = type == "Lab",
                    onClick = { type = "Lab" },
                    label = { Text("Lab") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Day Selection
            ExposedDropdownMenuBox(
                expanded = showDayDropdown,
                onExpandedChange = { showDayDropdown = it }
            ) {
                OutlinedTextField(
                    value = day,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Day") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDayDropdown) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = showDayDropdown,
                    onDismissRequest = { showDayDropdown = false }
                ) {
                    days.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                day = option
                                showDayDropdown = false
                            }
                        )
                    }
                }
            }
            
            // Time Inputs (Simple text for now, could be TimePicker)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start (HH:MM)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End (HH:MM)") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }
            
            // Color Selection
            Text("Color", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                colors.forEach { color ->
                    val isSelected = color == selectedColor
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(android.graphics.Color.parseColor(color)), CircleShape)
                            .clickable { selectedColor = color },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Save Button
            Button(
                onClick = {
                    if (title.isNotEmpty() && day.isNotEmpty()) {
                        scope.launch {
                            val newItem = ScheduleItem(
                                id = UUID.randomUUID().toString(),
                                title = title,
                                type = type.uppercase(),
                                day = day,
                                startTime = startTime,
                                endTime = endTime,
                                room = room,
                                instructor = null,
                                notes = null,
                                color = selectedColor
                            )
                            scheduleDataStore.add(newItem)
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Save Class", fontWeight = FontWeight.Bold)
            }
        }
    }
}
