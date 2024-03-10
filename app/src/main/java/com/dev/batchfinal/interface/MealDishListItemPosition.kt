package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.meal_dish_model.MealDishData

interface MealDishListItemPosition<T> {

    fun onMealDishListItemPosition(item: List<MealDishData>, position: T)
    fun onMealDishSelectItemPosition(item: List<MealDishData>, position: T)
}