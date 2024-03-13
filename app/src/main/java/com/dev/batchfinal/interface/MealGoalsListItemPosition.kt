package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoals

interface MealGoalsListItemPosition<T> {

    fun onMealGoalsListItemPosition(item: List<MealGoals>, position: T)
}