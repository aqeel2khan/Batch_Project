package com.dev.batchfinal.model.meal_dish_model


import com.google.gson.annotations.SerializedName

data class MealDishResponse(
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
    val `data`: ArrayList<MealDishData>,
    @SerializedName("status")
    val status: String
)

data class MealDishData(
    @SerializedName("avg_preparation_time")
    val avgPreparationTime: String,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_ar")
    val descriptionAr: String,
    @SerializedName("dish_id")
    val dishId: Int,
    @SerializedName("meal_id")
    val mealId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_ar")
    val nameAr: String,
    @SerializedName("order_in_menu")
    val orderInMenu: Int,
    @SerializedName("price")
    val price: String
)

class Error