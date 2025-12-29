package com.inguide.app.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inguide.app.data.ChatDataStore
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import com.inguide.app.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val chatStore = remember { ChatDataStore.getInstance(context) }
    
    // State
    var currentChatId by remember { mutableStateOf<String?>(null) }
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    var inputText by remember { mutableStateOf("") }
    var isDrawerOpen by remember { mutableStateOf(false) }
    var allChats by remember { mutableStateOf<List<Chat>>(emptyList()) }
    
    // Scrolling
    val listState = rememberLazyListState()
    
    // Initial Load
    LaunchedEffect(Unit) {
        allChats = chatStore.getChats()
    }
    
    // Load messages when chat changes
    LaunchedEffect(currentChatId) {
        if (currentChatId != null) {
            messages = chatStore.getMessages(currentChatId!!)
            // Scroll to bottom
            delay(100)
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        } else {
            messages = emptyList()
        }
    }
    
    DrawerNavigationWrapper(
        modifier = modifier,
        isDrawerOpen = isDrawerOpen,
        onCloseDrawer = { isDrawerOpen = false },
        drawerContent = {
            ChatHistoryDrawer(
                chats = allChats,
                currentChatId = currentChatId,
                onChatSelected = { chat ->
                    currentChatId = chat.id
                    isDrawerOpen = false
                },
                onNewChat = {
                    currentChatId = null
                    isDrawerOpen = false
                },
                onDeleteChat = { chatId ->
                    scope.launch {
                        chatStore.deleteChat(chatId)
                        allChats = chatStore.getChats()
                        if (currentChatId == chatId) currentChatId = null
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                ChatTopBar(
                    onHistoryClick = { isDrawerOpen = true },
                    onNewChatClick = { currentChatId = null }
                )
            },
            bottomBar = {
                ChatInputArea(
                    value = inputText,
                    onValueChange = { inputText = it },
                    onSend = {
                        if (inputText.isNotBlank()) {
                            val text = inputText
                            inputText = ""
                            scope.launch {
                                // 1. Create chat if null
                                val chatId = currentChatId ?: chatStore.createChat(title = text.take(20)).id
                                currentChatId = chatId
                                
                                // 2. Add user message
                                chatStore.addMessage(chatId, text, "user")
                                messages = chatStore.getMessages(chatId)
                                
                                // 3. Simulate Bot Response
                                delay(1000)
                                val response = "I'm the Guide Bot. I can help you find locations or check schedules. (Simulation: You said '$text')"
                                chatStore.addMessage(chatId, response, "assistant")
                                messages = chatStore.getMessages(chatId)
                                
                                // Update history list
                                allChats = chatStore.getChats()
                                
                                // Scroll
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (messages.isEmpty() && currentChatId == null) {
                    EmptyChatState()
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(messages) { message ->
                            MessageBubble(message = message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerNavigationWrapper(
    modifier: Modifier = Modifier,
    isDrawerOpen: Boolean,
    onCloseDrawer: () -> Unit,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    // Simple overlay drawer implementation
    Box(modifier = modifier.fillMaxSize()) {
        content()
        
        if (isDrawerOpen) {
            // Scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(onClick = onCloseDrawer)
            )
            // Drawer
            Row {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    drawerContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    onHistoryClick: () -> Unit,
    onNewChatClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Guide Bot",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Green, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Online",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onHistoryClick) {
                Icon(Icons.Default.History, contentDescription = "History")
            }
        },
        actions = {
            IconButton(onClick = onNewChatClick) {
                Icon(Icons.Default.Add, contentDescription = "New Chat")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Info, contentDescription = "Info")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun ChatHistoryDrawer(
    chats: List<Chat>,
    currentChatId: String?,
    onChatSelected: (Chat) -> Unit,
    onNewChat: () -> Unit,
    onDeleteChat: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onNewChat,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("New Chat")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "History",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chats) { chat ->
                NavigationDrawerItem(
                    label = { Text(chat.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    selected = chat.id == currentChatId,
                    onClick = { onChatSelected(chat) },
                    shape = RoundedCornerShape(8.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Primary.copy(alpha = 0.1f),
                        selectedTextColor = Primary
                    )
                )
            }
        }
    }
}

@Composable
fun EmptyChatState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(20.dp),
            color = Primary.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Info, // Placeholder for Bot Icon
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Start a Conversation",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Send a message to begin chatting with Guide Bot.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Suggestion Chips
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(
                onClick = {},
                label = { Text("AI-powered") },
                icon = { Icon(Icons.Default.Info, null, modifier = Modifier.size(16.dp)) }
            )
            SuggestionChip(
                onClick = {},
                label = { Text("Schedules") },
                icon = { Icon(Icons.Default.History, null, modifier = Modifier.size(16.dp)) }
            )
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val isUser = message.role == "user"
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        // Name and Time
        if (!isUser) {
            Text(
                text = "Guide Bot",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
            )
        }
        
        Surface(
            shape = if (isUser) 
                RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)
            else 
                RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp),
            color = if (isUser) Primary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(16.dp),
                color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(message.timestamp)),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun ChatInputArea(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Image, contentDescription = "Image", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = { Text("Type your message...") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            if (value.isBlank()) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                IconButton(
                    onClick = onSend,
                    modifier = Modifier
                        .background(Primary, CircleShape)
                        .size(48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                }
            }
        }
    }
}
