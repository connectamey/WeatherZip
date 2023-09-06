package com.amey.weatherzip.model

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("observations")
    val observations: List<Observation>
)