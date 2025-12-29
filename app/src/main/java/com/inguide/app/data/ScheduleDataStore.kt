package com.inguide.app.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inguide.app.data.model.ScheduleItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScheduleDataStore(context: Context) {
    
    private val dataStoreManager = DataStoreManager.getInstance(context)
    private val gson = Gson()
    
    // Memory cache
    private var cache: List<ScheduleItem>? = null
    private var isCacheValid = false
    
    private val _scheduleFlow = MutableStateFlow<List<ScheduleItem>>(emptyList())
    val scheduleFlow: Flow<List<ScheduleItem>> = _scheduleFlow.asStateFlow()
    
    companion object {
        @Volatile
        private var INSTANCE: ScheduleDataStore? = null
        
        fun getInstance(context: Context): ScheduleDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScheduleDataStore(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    suspend fun getAll(): List<ScheduleItem> {
        // Return cached data if valid
        if (isCacheValid && cache != null) {
            return cache!!
        }
        
        return try {
            val json = dataStoreManager.getScheduleData()
            val items = if (json != null) {
                val type = object : TypeToken<List<ScheduleItem>>() {}.type
                gson.fromJson<List<ScheduleItem>>(json, type)
            } else {
                emptyList()
            }
            
            // Update cache
            cache = items
            isCacheValid = true
            _scheduleFlow.value = items
            
            items
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    suspend fun save(items: List<ScheduleItem>) {
        try {
            // Update cache immediately (optimistic update)
            cache = items
            isCacheValid = true
            _scheduleFlow.value = items
            
            val json = gson.toJson(items)
            dataStoreManager.saveScheduleData(json)
        } catch (e: Exception) {
            e.printStackTrace()
            // Invalidate cache on error
            isCacheValid = false
            throw e
        }
    }
    
    suspend fun add(item: ScheduleItem) {
        val items = getAll().toMutableList()
        items.add(item)
        save(items)
    }
    
    suspend fun update(id: String, updatedItem: ScheduleItem) {
        val items = getAll().toMutableList()
        val index = items.indexOfFirst { it.id == id }
        if (index != -1) {
            items[index] = updatedItem
            save(items)
        }
    }
    
    suspend fun delete(id: String) {
        val items = getAll().toMutableList()
        items.removeAll { it.id == id }
        save(items)
    }
    
    suspend fun getById(id: String): ScheduleItem? {
        return getAll().find { it.id == id }
    }
    
    suspend fun getByDay(day: String): List<ScheduleItem> {
        return getAll().filter { it.day == day }
            .sortedBy { it.startTime }
    }
    
    suspend fun clear() {
        cache = null
        isCacheValid = false
        _scheduleFlow.value = emptyList()
        save(emptyList())
    }
    
    // Invalidate cache (call when data might have changed externally)
    fun invalidateCache() {
        isCacheValid = false
    }
}
