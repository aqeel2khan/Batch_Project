package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.question.model.meal_tags.MealTags

interface MealTagsListItemPosition<T> {

    fun onMealTagsListItemPosition(item: List<MealTags>, position: T)
}