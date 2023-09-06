package com.amey.weatherzip.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface WeatherDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData)

    @Query("SELECT * FROM weather_data WHERE zipCode = :zipCode")
     fun getWeatherData(zipCode: String): Observable<WeatherData?>
}