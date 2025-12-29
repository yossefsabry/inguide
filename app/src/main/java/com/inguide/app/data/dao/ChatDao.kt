package com.inguide.app.data.dao

import androidx.room.*
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chats ORDER BY updatedAt DESC")
    fun getAllChatsFlow(): Flow<List<Chat>>
    
    @Query("SELECT * FROM chats ORDER BY updatedAt DESC")
    suspend fun getAllChats(): List<Chat>
    
    @Query("SELECT * FROM chats WHERE id = :chatId")
    suspend fun getChatById(chatId: String): Chat?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)
    
    @Update
    suspend fun updateChat(chat: Chat)
    
    @Delete
    suspend fun deleteChat(chat: Chat)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessagesFlow(chatId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    suspend fun getMessages(chatId: String): List<Message>
    
    @Insert
    suspend fun insertMessage(message: Message): Long
    
    @Query("DELETE FROM messages WHERE chatId = :chatId")
    suspend fun deleteMessagesForChat(chatId: String)
}
