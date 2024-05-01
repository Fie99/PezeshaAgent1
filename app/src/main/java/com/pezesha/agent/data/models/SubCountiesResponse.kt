package com.pezesha.agent.data.models


import com.google.gson.annotations.SerializedName

data class SubCountiesResponse(
    @SerializedName("data")
    val subCounties: List<SubCounty>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class SubCounty(
    @SerializedName("name")
    val name: String
)