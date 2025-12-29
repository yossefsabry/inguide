package com.inguide.app.ui.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inguide.app.ui.theme.DesignSystem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            text = "Hello, Alex",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Computer Science Dept.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DesignSystem.Colors.DashboardBackground
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystem.Colors.DashboardBackground)
                .padding(paddingValues)
                .padding(horizontal = DesignSystem.Dimensions.PaddingMedium)
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }

            // 1. Attendance Score Card
            item {
                AttendanceScoreCard()
                Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingLarge))
            }

            // 2. Quick Actions
            item {
                QuickActionsRow()
                Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingLarge))
            }

            // 3. Critical Warning
            item {
                CriticalWarningCard()
                Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingLarge))
            }

            // 4. Upcoming Critical
            item {
                Text(
                    text = "Upcoming Critical",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        UpcomingClassCard(
                            title = "Data Structures Lab",
                            subtitle = "Lab 304 • Mr. Anderson",
                            time = "10:00 AM",
                            status = "Attendance Required",
                            statusColor = DesignSystem.Colors.DashboardWarningRed,
                            note = "Absence limit reached (2/3)"
                        )
                    }
                    item {
                         UpcomingClassCard(
                            title = "Calculus II",
                            subtitle = "Auditorium B • Dr. Smith",
                            time = "12:30 PM",
                            status = "Caution",
                            statusColor = DesignSystem.Colors.DashboardCautionYellow,
                            note = "You have 1 \"skip\" left"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingLarge))
            }

            // 5. Course Breakdown
            item {
                Text(
                    text = "Course Breakdown",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape,
                    colors = CardDefaults.cardColors(containerColor = DesignSystem.Colors.DashboardCardBackground)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CourseProgressItem("Data Structures", 0.72f, DesignSystem.Colors.DashboardWarningRed, "72% (Risk)", "Suggestion: Attend next 3 sessions to reach 80%")
                        Spacer(modifier = Modifier.height(16.dp))
                        CourseProgressItem("Calculus II", 0.81f, DesignSystem.Colors.DashboardCautionYellow, "81% (Warning)", null)
                        Spacer(modifier = Modifier.height(16.dp))
                        CourseProgressItem("Web Development", 0.98f, DesignSystem.Colors.FeatureGreen, "98% (Safe)", null)
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        OutlinedButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.fillMaxWidth(),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = DesignSystem.Colors.FeatureBlue)
                        ) {
                            Text("View Full Report")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(DesignSystem.Dimensions.PaddingLarge))
            }

            // 6. Recent Activity
            item {
                 Text(
                    text = "Recent Activity",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = DesignSystem.Shapes.CardShape,
                    colors = CardDefaults.cardColors(containerColor = DesignSystem.Colors.DashboardCardBackground)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = DesignSystem.Colors.FeatureGreen.copy(alpha = 0.1f)
                        ) {
                             Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = DesignSystem.Colors.FeatureGreen, modifier = Modifier.size(20.dp))
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                "Attendance Marked",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Marked present for Web Development lecture.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            "2h ago",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.4f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun AttendanceScoreCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = DesignSystem.Shapes.BottomSheetShape, // Using BottomSheetShape (Top rounded 24dp) or just a large rounded shape. Let's stick to CardShape or create a LargeCardShape if needed. The original was 24.dp. BottomSheetShape is top 24.dp. Let's use CardShape (16.dp) for consistency, or simply a custom shape if strictly needed. I'll use CardShape for uniformity.
        colors = CardDefaults.cardColors(containerColor = DesignSystem.Colors.DashboardCardBackground)
    ) {
        Column {
            // Top Section
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "ATTENDANCE SCORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.6f),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "85%",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "You are in the safe zone",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
                
                // Circular Progress
                Box(contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(64.dp)) {
                        drawArc(
                            color = DesignSystem.Colors.FeatureBlue.copy(alpha = 0.2f),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = DesignSystem.Colors.FeatureBlue,
                            startAngle = -90f,
                            sweepAngle = 0.85f * 360f,
                            useCenter = false,
                            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = DesignSystem.Colors.FeatureBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Graph Placeholder (Simplified)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Simulating bars
                val heights = listOf(40, 60, 0, 50, 70) // Wednesday is 0/Red
                val days = listOf("M", "T", "W", "T", "F")
                
                heights.forEachIndexed { index, h ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height((h/2).dp)
                                .background(
                                    color = if (index == 2) DesignSystem.Colors.DashboardWarningRed else DesignSystem.Colors.FeatureBlue.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = days[index],
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.4f)
                        )
                    }
                }
            }
            
             Spacer(modifier = Modifier.height(10.dp))

            // Bottom Alert
            Surface(
                color = DesignSystem.Colors.DashboardWarningRed.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = DesignSystem.Colors.DashboardWarningRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Absent on Wednesday",
                        style = MaterialTheme.typography.bodySmall,
                        color = DesignSystem.Colors.DashboardWarningRed,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "Tap to justify",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickActionButton("Scan In", Icons.Default.QrCodeScanner, DesignSystem.Colors.FeatureBlue)
        QuickActionButton("Excuse", Icons.Default.Assignment, DesignSystem.Colors.FeaturePurple)
        QuickActionButton("History", Icons.Default.History, DesignSystem.Colors.FeatureGreen)
        QuickActionButton("Limits", Icons.Default.Speed, DesignSystem.Colors.FeatureOrange)
    }
}

@Composable
fun QuickActionButton(text: String, icon: ImageVector, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = DesignSystem.Shapes.CardShape,
            color = DesignSystem.Colors.DashboardCardBackground
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = color
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun CriticalWarningCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = DesignSystem.Shapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1E24)) // Dark Reddish, maybe make a const for this too?
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = DesignSystem.Colors.DashboardWarningRed,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Critical Warning",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DesignSystem.Colors.DashboardWarningRed
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "You have reached 85% absence in \"Data Structures\". Attending the next 2 lectures is mandatory to avoid penalties.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = DesignSystem.Colors.DashboardWarningRed.copy(alpha = 0.2f)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                shape = DesignSystem.Shapes.InputShape // Small rounded
            ) {
                Text("View Policy", color = DesignSystem.Colors.DashboardWarningRed, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun UpcomingClassCard(
    title: String,
    subtitle: String,
    time: String,
    status: String,
    statusColor: Color,
    note: String
) {
    Card(
        modifier = Modifier.width(280.dp),
        shape = DesignSystem.Shapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = DesignSystem.Colors.DashboardCardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Badge & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = statusColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp) // Very small shape, can keep or use InputShape
                ) {
                    Text(
                        text = status,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    note,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.4f)
                )
            }
        }
    }
}

@Composable
fun CourseProgressItem(
    name: String,
    progress: Float,
    color: Color,
    statusText: String,
    suggestion: String?
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                statusText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color(0xFF2C3040)
        )
        if (suggestion != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                suggestion,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 10.sp
            )
        }
    }
}
