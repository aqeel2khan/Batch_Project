package com.dev.batchfinal.app_modules.question.model.meal_goals


import com.google.gson.annotations.SerializedName

data class MealGoalsResponse(
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
    val `data`: List<MealGoals>,
    @SerializedName("status")
    val status: String
)

data class MealGoals(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("selected")
    var selected: String = "0"
)

class Error