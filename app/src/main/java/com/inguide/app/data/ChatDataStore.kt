package com.inguide.app.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ChatDataStore(context: Context) {
    
    private val dataStoreManager = DataStoreManager.getInstance(context)
    private val gson = Gson()
    
    // In-memory cache
    private var chatsCache: MutableList<Chat> = mutableListOf()
    private var messagesCache: MutableMap<String, MutableList<Message>> = mutableMapOf()
    private var isCacheValid = false
    
    private val _chatsFlow = MutableStateFlow<List<Chat>>(emptyList())
    val chatsFlow = _chatsFlow.asStateFlow()
    
    companion object {
        @Volatile
        private var INSTANCE: ChatDataStore? = null
        
        fun getInstance(context: Context): ChatDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ChatDataStore(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    // Data structure for JSON storage
    private data class ChatExport(
        val chats: List<Chat>,
        val messages: Map<String, List<Message>>
    )
    
    private suspend fun ensureLoaded() {
        if (!isCacheValid) {
            val json = dataStoreManager.getChatData()
            if (json != null) {
                try {
                    val type = object : TypeToken<ChatExport>() {}.type
                    val export = gson.fromJson<ChatExport>(json, type)
                    chatsCache = export.chats.toMutableList()
                    messagesCache = export.messages.mapValues { it.value.toMutableList() }.toMutableMap()
                } catch (e: Exception) {
                    e.printStackTrace()
                    chatsCache = mutableListOf()
                    messagesCache = mutableMapOf()
                }
            } else {
                chatsCache = mutableListOf()
                messagesCache = mutableMapOf()
            }
            isCacheValid = true
            _chatsFlow.value = chatsCache
        }
    }
    
    private suspend fun save() {
        val export = ChatExport(chatsCache, messagesCache)
        val json = gson.toJson(export)
        dataStoreManager.saveChatData(json)
        _chatsFlow.value = chatsCache.toList()
    }
    
    suspend fun createChat(title: String = "New Chat"): Chat {
        ensureLoaded()
        val chat = Chat(
            id = UUID.randomUUID().toString(),
            name = title,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        chatsCache.add(0, chat) // Add to top
        messagesCache[chat.id] = mutableListOf()
        save()
        return chat
    }
    
    suspend fun getChats(): List<Chat> {
        ensureLoaded()
        return chatsCache.sortedByDescending { it.updatedAt }
    }
    
    suspend fun getMessages(chatId: String): List<Message> {
        ensureLoaded()
        return messagesCache[chatId]?.sortedBy { it.timestamp } ?: emptyList()
    }
    
    suspend fun addMessage(chatId: String, content: String, role: String): Message {
        ensureLoaded()
        val message = Message(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            content = content,
            role = role,
            timestamp = System.currentTimeMillis()
        )
        
        // Add message
        val list = messagesCache.getOrPut(chatId) { mutableListOf() }
        list.add(message)
        
        // Update chat timestamp and title if it's the first user message
        val chatIndex = chatsCache.indexOfFirst { it.id == chatId }
        if (chatIndex != -1) {
            val chat = chatsCache[chatIndex]
            val newTitle = if (list.size == 1 && role == "user") content.take(30) else chat.name
            
            chatsCache[chatIndex] = chat.copy(
                updatedAt = System.currentTimeMillis(),
                name = newTitle
            )
        }
        
        save()
        return message
    }
    
    suspend fun deleteChat(chatId: String) {
        ensureLoaded()
        chatsCache.removeAll { it.id == chatId }
        messagesCache.remove(chatId)
        save()
    }
    
    suspend fun clearAll() {
        chatsCache.clear()
        messagesCache.clear()
        save()
    }
}
