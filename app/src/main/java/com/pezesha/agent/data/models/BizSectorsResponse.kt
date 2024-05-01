package com.pezesha.agent.data.models


import com.google.gson.annotations.SerializedName

data class BizSectorsResponse(
    @SerializedName("data")
    val bizSectors: List<BizSector>?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)

data class BizSector(
    @SerializedName("name")
    val name: String,
)
