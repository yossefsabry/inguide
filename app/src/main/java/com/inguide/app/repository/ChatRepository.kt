package com.inguide.app.repository

import com.inguide.app.data.dao.ChatDao
import com.inguide.app.data.dao.MessageDao
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import kotlinx.coroutines.flow.Flow

class ChatRepository(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
) {
    
    fun getAllChats(): Flow<List<Chat>> {
        return chatDao.getAllChatsFlow()
    }
    
    suspend fun getChatById(chatId: String): Chat? {
        return chatDao.getChatById(chatId)
    }
    
    suspend fun createChat(chat: Chat) {
        chatDao.insertChat(chat)
    }
    
    suspend fun updateChat(chat: Chat) {
        chatDao.updateChat(chat)
    }
    
    suspend fun deleteChat(chat: Chat) {
        messageDao.deleteMessagesForChat(chat.id)
        chatDao.deleteChat(chat)
    }
    
    fun getMessagesForChat(chatId: String): Flow<List<Message>> {
        return messageDao.getMessagesFlow(chatId)
    }
    
    suspend fun addMessage(message: Message): Long {
        return messageDao.insertMessage(message)
    }
    
    suspend fun getMessagesList(chatId: String): List<Message> {
        return messageDao.getMessages(chatId)
    }
}
