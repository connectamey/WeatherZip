package com.amey.weatherzip.model.api
import com.amey.weatherzip.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/v3/report")
    fun getWeatherReport(
        @Query("products") products: String,
        @Query("zipCode") zipCode: String,
        @Query("apiKey") apiKey: String
    ): Observable<WeatherResponse>
}