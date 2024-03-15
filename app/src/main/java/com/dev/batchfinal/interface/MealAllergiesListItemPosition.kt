package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.question.model.meal_allergies.MealAllergies


interface MealAllergiesListItemPosition<T> {

    fun onMealAllergiesListItemPosition(item: List<MealAllergies>, position: T)
}