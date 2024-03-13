package com.dev.batchfinal.app_modules.question.model.all_question


import com.google.gson.annotations.SerializedName

data class SubmitAllQueResponse(
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
    @SerializedName("data")
    val `internal`: CalParDay,
    @SerializedName("status")
    val status: String
)

data class CalParDay(
    @SerializedName("avg_cal_per_day")
    val avgCalPerDay: String
)

class Error