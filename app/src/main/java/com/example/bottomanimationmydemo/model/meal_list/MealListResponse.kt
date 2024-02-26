package com.example.bottomanimationmydemo.model.meal_list

data class MealListResponse(
    val `data`: Data,
    val error: Error,
    val message: String,
    val status: Boolean
)

data class Data(
    val `mealList`: List<MealList>,
    val recordsTotal: Int,
    val status: String
)

data class MealList(
    val avg_cal_per_day: String,
    val batch_id: Int,
    val created_at: String,
    val description: String,
    val description_ar: String,
    val discount: Int,
    val duration: String,
    val goal_id: Int,
    val id: Int,
    val name: String,
    val name_ar: String,
    val price: String,
    val restaurant_id: Int,
    val status: Int,
    val updated_at: String
)

class Error