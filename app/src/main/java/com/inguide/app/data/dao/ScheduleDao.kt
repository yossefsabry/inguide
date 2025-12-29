package com.inguide.app.data.dao

import androidx.room.*
import com.inguide.app.data.model.ScheduleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule_items ORDER BY day, startTime")
    fun getAllFlow(): Flow<List<ScheduleItem>>
    
    @Query("SELECT * FROM schedule_items")
    suspend fun getAll(): List<ScheduleItem>
    
    @Query("SELECT * FROM schedule_items WHERE day = :day ORDER BY startTime")
    suspend fun getByDay(day: String): List<ScheduleItem>
    
    @Query("SELECT * FROM schedule_items WHERE id = :id")
    suspend fun getById(id: String): ScheduleItem?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ScheduleItem)
    
    @Update
    suspend fun update(item: ScheduleItem)
    
    @Delete
    suspend fun delete(item: ScheduleItem)
    
    @Query("DELETE FROM schedule_items WHERE id = :id")
    suspend fun deleteById(id: String)
}
