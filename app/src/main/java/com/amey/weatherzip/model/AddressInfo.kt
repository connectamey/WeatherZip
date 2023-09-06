package com.amey.weatherzip.model

import com.google.gson.annotations.SerializedName

data class AddressInfo(
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("city")
    val city: String
)