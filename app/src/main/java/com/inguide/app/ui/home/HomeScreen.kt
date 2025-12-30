package com.inguide.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.inguide.app.navigation.Screen
import androidx.compose.ui.res.painterResource
import com.inguide.app.R
import com.inguide.app.data.DataStoreManager
import com.inguide.app.data.ScheduleDataStore
import com.inguide.app.data.model.ScheduleItem
import com.inguide.app.ui.components.*
import com.inguide.app.ui.theme.DesignSystem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class QuickAction(
    val icon: ImageVector,
    val title: String,
    val gradient: List<Color>,
    val route: String
)

data class PopularLocation(
    val id: Int,
    val name: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scheduleDataStore = remember { ScheduleDataStore.getInstance(context) }
    val dataStoreManager = remember { DataStoreManager.getInstance(context) }
    
    var isLoading by remember { mutableStateOf(true) }
    var showProfileMenu by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("Student") }
    // Schedule items might be used later or removed if purely hardcoded now
    var scheduleItems by remember { mutableStateOf<List<ScheduleItem>>(emptyList()) }
    // Load data
    LaunchedEffect(Unit) {
        launch {
            userName = dataStoreManager.getUserName()
            scheduleItems = scheduleDataStore.getAll()
            delay(800) // Simulate loading
            isLoading = false
        }
    }
    
    ProfileMenu(
        visible = showProfileMenu,
        onDismiss = { showProfileMenu = false },
        navController = navController,
        userName = userName,
        isDarkTheme = isDarkTheme,
        onThemeToggle = onThemeToggle,
        onLogout = onLogout
    )

    if (isLoading) {
        LoadingScreen(modifier = modifier, isDarkTheme = isDarkTheme)
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "Logo",
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Welcome back,",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = userName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        
                        Surface(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { showProfileMenu = true }, // Show Menu
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                            // In real app, use Image
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "JP",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            
            item {
                // Search Bar
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = DesignSystem.Shapes.InputShape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Find a classroom, library, or café...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            item {
                // Top Buttons Row (Nearby, AR View)
                Text(
                    text = "Explore",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        QuickAccessButton(
                            title = "Nearby",
                            icon = Icons.Filled.Place,
                            color = DesignSystem.Colors.FeatureGreen,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToTab(1) } // Map
                        )
                        
                        QuickAccessButton(
                            title = "AR View",
                            icon = Icons.Filled.ViewInAr,
                            color = DesignSystem.Colors.FeaturePurple,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToTab(1) } // Map
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        QuickAccessButton(
                            title = "Schedule",
                            icon = Icons.Outlined.CalendarMonth,
                            color = DesignSystem.Colors.FeatureOrange,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToTab(2) } // Schedule
                        )
                        
                        QuickAccessButton(
                            title = "Chaty",
                            icon = Icons.Outlined.Chat,
                            color = DesignSystem.Colors.FeatureBlue,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToTab(3) } // Chat
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // Up Next Section (Moved Up)
            val nextItem = scheduleItems.firstOrNull() 
            if (nextItem != null) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Up Next",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(onClick = { onNavigateToTab(2) }) {
                            Text("See All", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Reusing Schedule Card Style
                    ScheduleItemCard(
                         item = nextItem,
                         modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            item {
                // Top Places Header
                Text(
                    text = "Top Places",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Hardcoded "Top Places" list
            val topPlaces = listOf(
                PopularLocation(1, "Student Center", Icons.Outlined.Storefront),
                PopularLocation(2, "Main Library", Icons.Outlined.MenuBook),
                PopularLocation(3, "Engineering Block", Icons.Outlined.Architecture),
                PopularLocation(4, "Central Park", Icons.Outlined.Park)
            )
            
            items(topPlaces) { location ->
                TopPlaceListItem(
                    location = location,
                    onClick = { onNavigateToTab(1) } // Navigate to Map
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
            

        }
    }
}

@Composable
fun QuickAccessButton(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    
    // Using Surface for card-like appearance
    Surface(
        modifier = modifier
            .height(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface, // Use surface color which adapts to dark/light
        shadowElevation = 2.dp
    ) {
         Box(modifier = Modifier.fillMaxSize()) {
            // Optional: Add subtle colored background tint if desired
             
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    color = color.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
         }
    }
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier, 
    isDarkTheme: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) {
                SkeletonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .height(100.dp),
                    shimmerColor = if (isDarkTheme) DesignSystem.Colors.ShimmerDark else DesignSystem.Colors.ShimmerLight
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    action: QuickAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    GradientCard(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        gradientColors = action.gradient
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = action.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ScheduleItemCard(
    item: ScheduleItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = DesignSystem.Shapes.InputShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(4.dp, 48.dp),
                color = Color(android.graphics.Color.parseColor(item.color ?: "#007AFF")),
                shape = RoundedCornerShape(2.dp)
            ) {}
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${item.startTime} - ${item.endTime} • ${item.room} • ${item.day}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Text(
                    text = item.type.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun TopPlaceListItem(
    location: PopularLocation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = DesignSystem.Shapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = DesignSystem.Shapes.InputShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = location.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}
