package com.example.deliveryapp.data.model

import com.google.gson.annotations.SerializedName

data class JsonPatch(
    @SerializedName("op") val op: String,
    @SerializedName("path") val path: String,
    @SerializedName("value") val value: Any?
)