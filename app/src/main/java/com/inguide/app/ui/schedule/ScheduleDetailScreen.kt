package com.inguide.app.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
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
import com.inguide.app.ui.theme.DesignSystem
import kotlinx.coroutines.launch
import com.inguide.app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailScreen(
    scheduleId: String,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scheduleDataStore = remember { ScheduleDataStore.getInstance(context) }
    
    var scheduleItem by remember { mutableStateOf<ScheduleItem?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    
    // Editable State
    var title by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("") }

    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    var showDayDropdown by remember { mutableStateOf(false) }

    val colors = listOf(
        "#007AFF", "#FF9500", "#34C759", "#AF52DE", "#FF2D55", "#5856D6"
    )

    LaunchedEffect(scheduleId) {
        val item = scheduleDataStore.getById(scheduleId)
        if (item != null) {
            scheduleItem = item
            title = item.title
            room = item.room
            startTime = item.startTime
            endTime = item.endTime
            day = item.day
            type = item.type
            selectedColor = item.color
        }
    }

    if (scheduleItem == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Class" else "Class Details") },
                navigationIcon = {
                    IconButton(onClick = { 
                        navController.navigate("${Screen.Main.route}?tab=2") {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (isEditing) {
                // Edit Mode
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Class Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.InputShape
                )
                
                OutlinedTextField(
                    value = room,
                    onValueChange = { room = it },
                    label = { Text("Room") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.InputShape
                )

                // Type
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FilterChip(
                        selected = type.equals("Lecture", ignoreCase = true),
                        onClick = { type = "Lecture" },
                        label = { Text("Lecture") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = type.equals("Lab", ignoreCase = true),
                        onClick = { type = "Lab" },
                        label = { Text("Lab") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Day
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
                        shape = DesignSystem.Shapes.InputShape
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

                // Time
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("Start") },
                        modifier = Modifier.weight(1f),
                        shape = DesignSystem.Shapes.InputShape
                    )
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("End") },
                        modifier = Modifier.weight(1f),
                        shape = DesignSystem.Shapes.InputShape
                    )
                }
                
                // Color
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
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                
                Button(
                    onClick = {
                        scope.launch {
                            val updatedItem = scheduleItem!!.copy(
                                title = title,
                                room = room,
                                type = type,
                                day = day,
                                startTime = startTime,
                                endTime = endTime,
                                color = selectedColor
                            )
                            scheduleDataStore.update(scheduleId, updatedItem)
                            isEditing = false
                            scheduleItem = updatedItem
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = DesignSystem.Shapes.ButtonShape
                ) {
                    Text("Save Changes")
                }

            } else {
                // View Mode
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Class Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        DetailItem("Subject", title)
                        DetailItem("Room", room)
                        DetailItem("Type", type)
                        DetailItem("Day", day)
                        DetailItem("Time", "$startTime - $endTime")
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                OutlinedButton(
                     onClick = {
                         scope.launch {
                             scheduleDataStore.delete(scheduleId)
                             navController.navigate("${Screen.Main.route}?tab=2") {
                                 popUpTo(Screen.Main.route) { inclusive = true }
                             }
                         }
                     },
                     modifier = Modifier.fillMaxWidth(),
                     colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                     border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Class")
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
