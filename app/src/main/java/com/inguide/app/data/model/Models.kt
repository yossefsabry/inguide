package com.inguide.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_items")
data class ScheduleItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val type: String, // "lecture" or "lab"
    val day: String, // "Sunday", "Monday", etc.
    val startTime: String, // "08:00"
    val endTime: String, // "10:00"
    val room: String,
    val instructor: String?,
    val notes: String?,
    val color: String, // Hex color
    val locationId: Long? = null
)

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey
    val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val id: String,
    val chatId: String,
    val content: String,
    val role: String, // "user", "assistant", "system"
    val timestamp: Long
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String? = null,
    val bio: String? = null
)

data class LocationPoint(
    val id: Long,
    val longitude: Double,
    val latitude: Double,
    val altitude: Double,
    val accuracy: Double,
    val timestamp: String,
    val label: String
)

data class MagneticFingerprint(
    val x: Int,
    val y: Int,
    val Bx: Double,
    val By: Double,
    val Bz: Double,
    val timestamp: String
)
