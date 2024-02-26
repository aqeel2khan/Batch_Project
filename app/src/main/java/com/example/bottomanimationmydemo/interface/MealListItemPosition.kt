package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.meal_list.MealList

interface MealListItemPosition<T> {

    fun onMealListItemPosition(item: MealList, position: T)
}