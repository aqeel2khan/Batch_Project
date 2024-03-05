package com.example.bottomanimationmydemo.`interface`

import com.example.bottomanimationmydemo.model.subscribe_list_model.MealSubscribeListResponse.InternalDatum


interface MealSubscribeListPosition<T> {

    fun onMealSubscribeListItemPosition(item: InternalDatum, position: T)
}