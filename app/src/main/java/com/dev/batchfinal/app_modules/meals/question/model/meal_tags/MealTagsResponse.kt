package com.dev.batchfinal.app_modules.question.model.meal_tags


import com.google.gson.annotations.SerializedName

data class MealTagsResponse(
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
    val `data`: List<MealTags>,
    @SerializedName("status")
    val status: String
)
data class MealTags(
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