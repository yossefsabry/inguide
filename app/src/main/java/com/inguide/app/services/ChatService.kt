package com.inguide.app.services

import com.inguide.app.data.model.Message
import java.util.UUID

class ChatService {
    
    companion object {
        private const val MAX_MESSAGE_LENGTH = 500
    }
    
    fun validateMessage(text: String): Boolean {
        if (text.isBlank()) {
            throw IllegalArgumentException("Message cannot be empty")
        }
        if (text.length > MAX_MESSAGE_LENGTH) {
            throw IllegalArgumentException("Message is too long (max $MAX_MESSAGE_LENGTH characters)")
        }
        return true
    }
    
    fun generateAIResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()
        
        return when {
            lowerMessage.contains("schedule") || lowerMessage.contains("class") -> {
                "I can help you manage your schedule. Would you like to add a new class or view your existing schedule?"
            }
            lowerMessage.contains("location") || lowerMessage.contains("where") || lowerMessage.contains("find") -> {
                "I can help you navigate to any location on campus. What location are you looking for?"
            }
            lowerMessage.contains("help") || lowerMessage.contains("what can you do") -> {
                "I'm your InGuide assistant! I can help you with:\n• Navigation around campus\n• Schedule management\n• Finding locations\n• Campus information\n\nWhat would you like to know?"
            }
            lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey") -> {
                "Hello! How can I assist you today?"
            }
            lowerMessage.contains("thanks") || lowerMessage.contains("thank you") -> {
                "You're welcome! Let me know if you need anything else."
            }
            lowerMessage.contains("room") || lowerMessage.contains("ng-") || lowerMessage.contains("building") -> {
                "I can help you find that room. Would you like me to show you the way on the map?"
            }
            lowerMessage.contains("time") || lowerMessage.contains("when") -> {
                "Let me check your schedule for you. What specific time or day are you asking about?"
            }
            else -> {
                "I understand you're asking about: \"$userMessage\". How can I assist you further?"
            }
        }
    }
    
    fun createUserMessage(chatId: String, content: String): Message {
        return Message(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            content = content,
            role = "user",
            timestamp = System.currentTimeMillis()
        )
    }
    
    fun createAIMessage(chatId: String, content: String): Message {
        return Message(
            id = UUID.randomUUID().toString(),
            chatId = chatId,
            content = content,
            role = "assistant",
            timestamp = System.currentTimeMillis()
        )
    }
}
