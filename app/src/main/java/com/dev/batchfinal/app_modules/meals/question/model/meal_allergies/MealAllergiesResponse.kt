package com.dev.batchfinal.app_modules.question.model.meal_allergies


import com.google.gson.annotations.SerializedName

data class MealAllergiesResponse(
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
    val `data`: List<MealAllergies>,
    @SerializedName("status")
    val status: String
)

data class MealAllergies(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("selected")
    var selected: String = "0"
)

class Error