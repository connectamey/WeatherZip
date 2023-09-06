package com.amey.weatherzip.model

import com.google.gson.annotations.SerializedName

data class Observation(
    @SerializedName("place")
    val place: PlaceInfo,
    @SerializedName("daylight")
    val daylight: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("skyInfo")
    val skyInfo: Int,
    @SerializedName("skyDesc")
    val skyDesc: String,
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("temperatureDesc")
    val temperatureDesc: String,
    @SerializedName("comfort")
    val comfort: String,
    @SerializedName("highTemperature")
    val highTemperature: String,
    @SerializedName("lowTemperature")
    val lowTemperature: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("dewPoint")
    val dewPoint: Double,
    @SerializedName("precipitationProbability")
    val precipitationProbability: Int,
    @SerializedName("windSpeed")
    val windSpeed: Double,
    @SerializedName("windDirection")
    val windDirection: Int,
    @SerializedName("windDesc")
    val windDesc: String,
    @SerializedName("windDescShort")
    val windDescShort: String,
    @SerializedName("uvIndex")
    val uvIndex: Int,
    @SerializedName("uvDesc")
    val uvDesc: String,
    @SerializedName("barometerPressure")
    val barometerPressure: Double,
    @SerializedName("barometerTrend")
    val barometerTrend: String,
    @SerializedName("visibility")
    val visibility: Double,
    @SerializedName("iconId")
    val iconId: Int,
    @SerializedName("iconName")
    val iconName: String,
    @SerializedName("iconLink")
    val iconLink: String,
    @SerializedName("ageMinutes")
    val ageMinutes: Int,
    @SerializedName("activeAlerts")
    val activeAlerts: Int,
    @SerializedName("time")
    val time: String
)