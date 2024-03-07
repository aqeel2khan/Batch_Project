package com.dev.batchfinal.model.order_model


import com.google.gson.annotations.SerializedName

data class OrederCreateResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Error,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

data class Data(
    @SerializedName("order_id")
    val orderId: Int
)

class Error