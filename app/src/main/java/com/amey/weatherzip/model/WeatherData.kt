package com.amey.weatherzip.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather_data")
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val zipCode: String,
    val city: String,
    val state: String,
    val temperature: Double,
    val description: String,
    // Add other fields as needed
): Serializable