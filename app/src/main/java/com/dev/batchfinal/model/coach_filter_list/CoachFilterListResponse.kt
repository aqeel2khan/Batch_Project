package com.dev.batchfinal.model.coach_filter_list


import com.google.gson.annotations.SerializedName

data class CoachFilterListResponse(
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
    @SerializedName("experiences")
    val experiences: List<Experience>,
    @SerializedName("workouttypes")
    val workouttypes: List<Workouttype>
)

data class Experience(
    @SerializedName("experience")
    val experience: String,
    @SerializedName("id")
    val id: Int
)

data class Workouttype(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("workout_type")
    val workoutType: String
)

class Error