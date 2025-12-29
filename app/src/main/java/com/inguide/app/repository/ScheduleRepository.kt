package com.inguide.app.repository

import com.inguide.app.data.dao.ScheduleDao
import com.inguide.app.data.model.ScheduleItem
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val scheduleDao: ScheduleDao) {
    
    fun getAllScheduleItems(): Flow<List<ScheduleItem>> {
        return scheduleDao.getAllFlow()
    }
    
    suspend fun getScheduleItemsForDay(day: String): List<ScheduleItem> {
        return scheduleDao.getByDay(day)
    }
    
    suspend fun getScheduleItemById(id: String): ScheduleItem? {
        return scheduleDao.getById(id)
    }
    
    suspend fun addScheduleItem(item: ScheduleItem) {
        scheduleDao.insert(item)
    }
    
    suspend fun updateScheduleItem(item: ScheduleItem) {
        scheduleDao.update(item)
    }
    
    suspend fun deleteScheduleItem(item: ScheduleItem) {
        scheduleDao.delete(item)
    }
    
    suspend fun deleteScheduleItemById(id: String) {
        scheduleDao.deleteById(id)
    }
    
    suspend fun getAllScheduleItemsList(): List<ScheduleItem> {
        return scheduleDao.getAll()
    }
}
