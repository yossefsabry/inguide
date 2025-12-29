package com.inguide.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import com.inguide.app.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {
    
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()
    
    private val _currentChatMessages = MutableStateFlow<List<Message>>(emptyList())
    val currentChatMessages: StateFlow<List<Message>> = _currentChatMessages.asStateFlow()
    
    private val _currentChatId = MutableStateFlow<String?>(null)
    val currentChatId: StateFlow<String?> = _currentChatId.asStateFlow()
    
    init {
        loadChats()
    }
    
    private fun loadChats() {
        viewModelScope.launch {
            repository.getAllChats().collect { chatList ->
                _chats.value = chatList
            }
        }
    }
    
    fun selectChat(chatId: String) {
        _currentChatId.value = chatId
        viewModelScope.launch {
            repository.getMessagesForChat(chatId).collect { messages ->
                _currentChatMessages.value = messages
            }
        }
    }
    
    fun sendMessage(content: String, chatId: String? = null) {
        viewModelScope.launch {
            val activeChatId = chatId ?: _currentChatId.value ?: createNewChat()
            
            // Add user message
            val userMessage = Message(
                id = java.util.UUID.randomUUID().toString(),
                chatId = activeChatId,
                content = content,
                role = "user",
                timestamp = System.currentTimeMillis()
            )
            repository.addMessage(userMessage)
            
            // Simulate AI response
            val aiResponse = Message(
                id = java.util.UUID.randomUUID().toString(),
                chatId = activeChatId,
                content = generateAIResponse(content),
                role = "assistant",
                timestamp = System.currentTimeMillis() + 1000
            )
            repository.addMessage(aiResponse)
            
            // Update chat timestamp
            repository.getChatById(activeChatId)?.let { chat ->
                repository.updateChat(chat.copy(updatedAt = System.currentTimeMillis()))
            }
        }
    }
    
    private suspend fun createNewChat(): String {
        val chatId = UUID.randomUUID().toString()
        val chat = Chat(
            id = chatId,
            name = "New Chat",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        repository.createChat(chat)
        _currentChatId.value = chatId
        return chatId
    }
    
    private fun generateAIResponse(userMessage: String): String {
        // Simple response generation - in production, this would call an AI API
        return when {
            userMessage.contains("schedule", ignoreCase = true) -> 
                "I can help you manage your schedule. Would you like to add a new class or view your existing schedule?"
            userMessage.contains("location", ignoreCase = true) || userMessage.contains("where", ignoreCase = true) ->
                "I can help you navigate to any location on campus. What location are you looking for?"
            userMessage.contains("help", ignoreCase = true) ->
                "I'm your InGuide assistant! I can help you with navigation, schedule management, and campus information. What would you like to know?"
            else ->
                "I understand you're asking about: \"$userMessage\". How can I assist you further?"
        }
    }
    
    fun deleteChat(chat: Chat) {
        viewModelScope.launch {
            repository.deleteChat(chat)
            if (_currentChatId.value == chat.id) {
                _currentChatId.value = null
                _currentChatMessages.value = emptyList()
            }
        }
    }
}
