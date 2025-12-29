package com.inguide.app

object AppConfig {
    // API Configuration from BuildConfig
    const val API_URL = BuildConfig.API_URL
    const val API_KEY = BuildConfig.API_KEY
    
    // App Configuration
    const val APP_NAME = BuildConfig.APP_NAME
    const val PACKAGE_NAME = "com.inguide.app"
    
    // Feature Flags
    const val ENABLE_AI_CHAT = true
    const val ENABLE_LOCATION_TRACKING = true
    const val ENABLE_INDOOR_POSITIONING = true
}
