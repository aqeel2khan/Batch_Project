package com.dev.batchfinal.model.meal_detail_model


import com.google.gson.annotations.SerializedName

data class MealDetailResponse(
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
    val `data`: MealDetails,
    @SerializedName("status")
    val status: String
)

data class MealDetails(
    @SerializedName("avg_cal_per_day")
    val avgCalPerDay: String,
    @SerializedName("category_list")
    val categoryList: List<Category>,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_ar")
    val descriptionAr: String,
    @SerializedName("meal_image")
    val meal_image: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("snack_count")
    val snack_count: Int,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("meal_count")
    val mealCount: Int,
    @SerializedName("meal_type")
    val mealType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("goal_id")
    val goal_id: String
)

data class Category(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("isSelected")
    var isSelected: Boolean=false

)

class Error