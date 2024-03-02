package com.example.bottomanimationmydemo.model.meal_filter_model


import com.google.gson.annotations.SerializedName

data class MealFilterResponse(
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
    val `data`: GoalList,
    @SerializedName("status")
    val status: String
)

data class GoalList(
    @SerializedName("batch_goals")
    val batchGoals: List<BatchGoal>,
    @SerializedName("meal_calories")
    val mealCalories: List<MealCalory>,
    @SerializedName("meal_tags")
    val mealTags: List<MealTag>
)

data class BatchGoal(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String
)

data class MealCalory(
    @SerializedName("from_value")
    val fromValue: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("to_value")
    val toValue: Int
)

data class MealTag(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String
)

class Error