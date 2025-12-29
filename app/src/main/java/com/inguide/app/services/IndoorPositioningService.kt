package com.inguide.app.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.inguide.app.data.model.MagneticFingerprint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.pow
import kotlin.math.sqrt

data class IndoorPosition(
    val x: Int,
    val y: Int,
    val confidence: Float
)

class IndoorPositioningService(context: Context) : SensorEventListener {
    
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    
    private val _currentPosition = MutableStateFlow<IndoorPosition?>(null)
    val currentPosition: StateFlow<IndoorPosition?> = _currentPosition
    
    private val _magneticField = MutableStateFlow<FloatArray?>(null)
    val magneticField: StateFlow<FloatArray?> = _magneticField
    
    // This would be loaded from JSON file in production
    private var fingerprints: List<MagneticFingerprint> = emptyList()
    
    fun start() {
        sensorManager.registerListener(
            this,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL // 500ms interval
        )
    }
    
    fun stop() {
        sensorManager.unregisterListener(this)
    }
    
    fun loadFingerprints(data: List<MagneticFingerprint>) {
        fingerprints = data
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                val magneticValues = it.values.clone()
                _magneticField.value = magneticValues
                
                // Calculate position based on magnetic fingerprint
                calculatePosition(magneticValues[0], magneticValues[1], magneticValues[2])
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }
    
    private fun calculatePosition(bx: Float, by: Float, bz: Float) {
        if (fingerprints.isEmpty()) return
        
        // Find closest matching fingerprint using Euclidean distance
        var minDistance = Float.MAX_VALUE
        var bestMatch: MagneticFingerprint? = null
        
        fingerprints.forEach { fingerprint ->
            val distance = calculateEuclideanDistance(
                bx, by, bz,
                fingerprint.Bx.toFloat(),
                fingerprint.By.toFloat(),
                fingerprint.Bz.toFloat()
            )
            
            if (distance < minDistance) {
                minDistance = distance
                bestMatch = fingerprint
            }
        }
        
        bestMatch?.let { match ->
            // Calculate confidence (inverse of distance, normalized)
            val confidence = 1f / (1f + minDistance)
            
            _currentPosition.value = IndoorPosition(
                x = match.x,
                y = match.y,
                confidence = confidence
            )
        }
    }
    
    private fun calculateEuclideanDistance(
        bx1: Float, by1: Float, bz1: Float,
        bx2: Float, by2: Float, bz2: Float
    ): Float {
        return sqrt(
            (bx1 - bx2).pow(2) +
            (by1 - by2).pow(2) +
            (bz1 - bz2).pow(2)
        )
    }
    
    fun getPositionConfidence(): Float {
        return _currentPosition.value?.confidence ?: 0f
    }
    
    fun isPositionReliable(threshold: Float = 0.7f): Boolean {
        return getPositionConfidence() >= threshold
    }
}
