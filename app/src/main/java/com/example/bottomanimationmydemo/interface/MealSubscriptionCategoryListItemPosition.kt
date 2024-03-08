package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.meal_subscription_details_model.MealSubscriptionCategoryResponse


interface MealSubscriptionCategoryListItemPosition<T> {

    fun onMealSubscriptionCategoryListItemPosition(item: MutableList<MealSubscriptionCategoryResponse>, position: T)
}