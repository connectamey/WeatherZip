package com.amey.weatherzip.model

import com.google.gson.annotations.SerializedName

data class LocationInfo(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)