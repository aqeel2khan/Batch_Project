package com.dev.batchfinal.`interface`

import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListResponse

//import com.example.bottomanimationmydemo.model.subscribe_list_model.MealSubscribeListResponse.InternalDatum


interface MealSubscribeListPosition<T> {

    fun onMealSubscribeListItemPosition(item: MealSubscribeListResponse.InternalDatum, position: T)
}