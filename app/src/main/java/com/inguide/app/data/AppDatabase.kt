package com.inguide.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inguide.app.data.dao.ChatDao
import com.inguide.app.data.dao.MessageDao
import com.inguide.app.data.dao.ScheduleDao
import com.inguide.app.data.model.Chat
import com.inguide.app.data.model.Message
import com.inguide.app.data.model.ScheduleItem
import com.inguide.app.data.model.User

@Database(
    entities = [ScheduleItem::class, Chat::class, Message::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inguide_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
