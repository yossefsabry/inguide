package com.inguide.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inguide.app.data.model.ScheduleItem
import com.inguide.app.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {
    
    private val _scheduleItems = MutableStateFlow<List<ScheduleItem>>(emptyList())
    val scheduleItems: StateFlow<List<ScheduleItem>> = _scheduleItems.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadSchedule()
    }
    
    fun loadSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllScheduleItems().collect { items ->
                _scheduleItems.value = items
                _isLoading.value = false
            }
        }
    }
    
    fun addScheduleItem(item: ScheduleItem) {
        viewModelScope.launch {
            repository.addScheduleItem(item)
        }
    }
    
    fun updateScheduleItem(item: ScheduleItem) {
        viewModelScope.launch {
            repository.updateScheduleItem(item)
        }
    }
    
    fun deleteScheduleItem(item: ScheduleItem) {
        viewModelScope.launch {
            repository.deleteScheduleItem(item)
        }
    }
    
    fun getItemsForDay(day: String): List<ScheduleItem> {
        return _scheduleItems.value.filter { it.day == day }
            .sortedBy { it.startTime }
    }
}
