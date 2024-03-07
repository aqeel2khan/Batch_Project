package com.dev.batchfinal.model.meal_list


import com.google.gson.annotations.SerializedName

data class MealResponseList(
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
    val `data`: ArrayList<MealList>,
    @SerializedName("recordsTotal")
    val recordsTotal: Int,
    @SerializedName("status")
    val status: String
)

data class MealList(
    @SerializedName("avg_cal_per_day")
    val avgCalPerDay: String,
    @SerializedName("batch_id")
    val batchId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_ar")
    val descriptionAr: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("goal_id")
    val goalId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("meal_count")
    val mealCount: Int,
    @SerializedName("meal_tags")
    val mealTags: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("restaurant_id")
    val restaurantId: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)

class Error