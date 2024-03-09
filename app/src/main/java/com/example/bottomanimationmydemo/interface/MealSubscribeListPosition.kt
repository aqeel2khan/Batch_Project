package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.meals.meal_purchase.model.subscribe_list_model.MealSubscribeListResponse


interface MealSubscribeListPosition<T> {

    fun onMealSubscribeListItemPosition(item: MealSubscribeListResponse.InternalDatum, position: T)
}