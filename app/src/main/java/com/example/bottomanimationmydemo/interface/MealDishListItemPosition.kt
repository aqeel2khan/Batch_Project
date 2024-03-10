package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData

interface MealDishListItemPosition<T> {

    fun onMealDishListItemPosition(item: List<MealDishData>, position: T)
    fun onMealDishSelectItemPosition(item: List<MealDishData>, position: T)
}