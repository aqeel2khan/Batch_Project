package com.dev.batchfinal.`interface`

import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_subscription_details_model.MealSubscriptionCategoryResponse

interface MealSubscriptionCategoryListItemPosition<T> {

    fun onMealSubscriptionCategoryListItemPosition(category: MutableList<MealSubscriptionCategoryResponse>, position: Int)
}