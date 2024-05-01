package com.pezesha.agent.data.models


import com.google.gson.annotations.SerializedName

data class CountiesResponse(
    @SerializedName("data")
    val counties: List<County>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class County(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)