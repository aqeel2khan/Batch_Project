package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.meal_list.MealList

interface MealListItemPosition<T> {

    fun onMealListItemPosition(item: MealList, position: T)
}