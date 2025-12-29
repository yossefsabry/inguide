package com.inguide.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "inguide_prefs")

class DataStoreManager(private val context: Context) {
    
    companion object {
        private val SCHEDULE_KEY = stringPreferencesKey("schedule_items")
        private val CHAT_KEY = stringPreferencesKey("chat_data")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val THEME_KEY = stringPreferencesKey("theme_mode")
        
        @Volatile
        private var INSTANCE: DataStoreManager? = null
        
        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStoreManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    // Schedule Data
    suspend fun saveScheduleData(json: String) {
        context.dataStore.edit { preferences ->
            preferences[SCHEDULE_KEY] = json
        }
    }
    
    suspend fun getScheduleData(): String? {
        return context.dataStore.data.first()[SCHEDULE_KEY]
    }
    
    val scheduleDataFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SCHEDULE_KEY]
    }
    
    // Chat Data
    suspend fun saveChatData(json: String) {
        context.dataStore.edit { preferences ->
            preferences[CHAT_KEY] = json
        }
    }
    
    suspend fun getChatData(): String? {
        return context.dataStore.data.first()[CHAT_KEY]
    }
    
    val chatDataFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[CHAT_KEY]
    }
    
    // User Data
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }
    
    suspend fun getUserName(): String {
        return context.dataStore.data.first()[USER_NAME_KEY] ?: "Student"
    }
    
    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }
    
    suspend fun getUserEmail(): String {
        return context.dataStore.data.first()[USER_EMAIL_KEY] ?: "student@su.edu.eg"
    }
    
    // Theme
    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
    
    suspend fun getTheme(): String {
        return context.dataStore.data.first()[THEME_KEY] ?: "system"
    }
    
    val themeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "system"
    }
    
    // Clear all data
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
