package com.amey.weatherzip.model

import com.google.gson.annotations.SerializedName

data class PlaceInfo(
    @SerializedName("address")
    val address: AddressInfo,
    @SerializedName("location")
    val location: LocationInfo,
    @SerializedName("distance")
    val distance: Double
)